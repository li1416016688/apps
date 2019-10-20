package com.easyexam.apps.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.StudentDao;
import com.easyexam.apps.dao.StudentExaminationDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentExaminationService;
import com.easyexam.apps.utils.MarkingTestPapers;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional//增加事务
@Service
public class StudentExaminationServiceImpl implements StudentExaminationService {

    @Autowired
    private StudentExaminationDao studentExaminationDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private MarkingTestPapers markingTestPapers;
    @Override
    public List<ExaminationRoom> findAllExaminationRoom(Integer subjectId,String roomName) {
        List<ExaminationRoom> allExaminationRoom = studentExaminationDao.findAllExaminationRoom(subjectId,roomName);

        //根据返回值判断考试场次，
        if (allExaminationRoom.size() == 0){
            throw new MyException(ErrorCode.SHOW_EXAMINATION_ERROR,codeMsg.getShowExaminationError());
        }
        //如果选择考试场次，判断该试卷的开始时间和当前时间进行比较，超过时间抛异常提示
        if (roomName!=null && allExaminationRoom != null){
            Date beginTime = allExaminationRoom.get(0).getBeginTime();
            Date endTime = allExaminationRoom.get(0).getEndTime();
            if (beginTime.compareTo(new Date()) > 0){
                throw new MyException(ErrorCode.EXAM_TIME_NO_START,codeMsg.getExamTimeNoStart());
            }
            if (endTime.compareTo(new Date()) < 0){
                throw new MyException(ErrorCode.EXAM_TIME_OVER,codeMsg.getExamTimeOver());
            }
        }
        return allExaminationRoom;
    }

    @Override
    public StudentPaper createPaper(Integer paperId, String idCard) {
        List<PaperQuestion> paper = studentExaminationDao.createPaper(paperId);
        //创建PaperQues这个存四种试题的对象，取出这试题类型
        PaperQues paperQues = new PaperQues();
        List<QuesSingleChoose> singleChooses = new ArrayList<>();
        List<QuesMultipleChoose> quesMultipleChooses = new ArrayList<>();
        List<QuesJudge> quesJudges = new ArrayList<>();
        List<QuesQuestionsAnswers> quesQuestionsAnswers = new ArrayList<>();
        for (int i = 0; i < paper.size(); i++) {
            //题类型的id
            Integer questionType = paper.get(i).getQuestionType();
            //具体题的id
            Integer questionId = paper.get(i).getQuestionId();
            //获取分数
            Integer score = paper.get(i).getScore();
            if (questionType == 1){
                QuesSingleChoose singleChoose = studentDao.findQuesSingleChooseById(questionId);
                singleChoose.setQuesScore(score);
                singleChooses.add(singleChoose);
                continue;
            }
            if (questionType == 2){
                QuesMultipleChoose multipleChoose = studentDao.findQuesMultipleChooseById(questionId);
                multipleChoose.setQuesScore(score);
                quesMultipleChooses.add(multipleChoose);
                continue;
            }
            if (questionType == 3){
                QuesJudge judge = studentDao.findQuesJudgeById(questionId);
                judge.setQuesScore(score);
                quesJudges.add(judge);
                continue;
            }
            if (questionType == 4){
                QuesQuestionsAnswers questionsAnswer = studentDao.findQuesQuestionsAnswersById(questionId);
                questionsAnswer.setQuesScore(score);
                quesQuestionsAnswers.add(questionsAnswer);
                continue;
            }
        }
        //将对应类型的题目保存到paperQues中，这只是题的封装
        paperQues.setQuesSingleChooses(singleChooses);
        paperQues.setQuesMultipleChooses(quesMultipleChooses);
        paperQues.setQuesJudges(quesJudges);
        paperQues.setQuesQuestionsAnswers(quesQuestionsAnswers);
        if (paperQues == null){
            throw new MyException(ErrorCode.CREATE_PAPER_FAIL,codeMsg.getCreatePaperFail());
        }
        //以下将题的user进行封装，创建StudentPaper对象
        StudentPaper studentPaper = new StudentPaper();
        //将paperQues转化成JSONObject,存入studentPaper中
        studentPaper.setPaperQues((JSONObject) JSONObject.toJSON(paperQues));
        ExaminationRoom examinationRoom = studentExaminationDao.findOneByPaperId(paperId);
        studentPaper.setRoom(examinationRoom);
        return studentPaper;
    }

    //返回的是正确答案的和考生答案和考生信息的
    @Override
    public StudentPaper saveStudentPaper(Integer paperId, String idCard, Map<String,List<String>> map) {


        //在redis取出当时开始考试生成的试卷，含有答案，不含有考生信息，含有考场信息
        Object opaper2 = redisTemplate.opsForHash().get("easyexam","paperId"+paperId );
        StudentPaper paper2 = (StudentPaper)opaper2;
        System.out.println(paper2);
        //获取学生信息
        Student student = studentDao.studentLogin(idCard);
        System.out.println(student.getId());
        paper2.setStuId(student.getId());
        //假设获取前台四个关于答案的list集合,存的单选，多选，判断，简答的答案，考生未作答设置为空
        List<String> singleChooses = map.get("quesSingleChooses");
        List<String> multipleChooses = map.get("quesMultipleChooses");
        List<String> judges = map.get("quesJudges");
        List<String> questionsAnswers = map.get("quesQuestionsAnswers");

        //循环遍历，将考生答案，放进对应的试题
        JSONObject paperQues1 = paper2.getPaperQues();
        //json对象转化为java对象
        PaperQues paperQues = JSONObject.toJavaObject(paperQues1, PaperQues.class);
        //将paperQues里面的四个类型的题的考生答案设置对应起来
        //单选题
        List<QuesSingleChoose> quesSingleChooses = paperQues.getQuesSingleChooses();
        for (int i = 0; i < quesSingleChooses.size() ; i++) {
            quesSingleChooses.get(i).setStudentAnswer(singleChooses.get(i));
        }
        paperQues.setQuesSingleChooses(quesSingleChooses);
        //多选题
        List<QuesMultipleChoose> quesMultipleChooses = paperQues.getQuesMultipleChooses();
        for (int i = 0; i < quesMultipleChooses.size(); i++) {
            quesMultipleChooses.get(i).setStudentAnswer(multipleChooses.get(i));
        }
        paperQues.setQuesMultipleChooses(quesMultipleChooses);
        //判断题
        List<QuesJudge> quesJudges = paperQues.getQuesJudges();
        for (int i = 0; i < quesJudges.size(); i++) {
            quesJudges.get(i).setStudentAnswer(Integer.parseInt(judges.get(i)));//boolean类型的默认值是false
        }
        paperQues.setQuesJudges(quesJudges);
        //简答题
        List<QuesQuestionsAnswers> quesQuestionsAnswers = paperQues.getQuesQuestionsAnswers();
        for (int i = 0; i < quesQuestionsAnswers.size(); i++) {
            quesQuestionsAnswers.get(i).setStudentAnswer(questionsAnswers.get(i));
        }
        paperQues.setQuesQuestionsAnswers(quesQuestionsAnswers);

        paper2.setPaperQues((JSONObject) JSONObject.toJSON(paperQues));
        //将学生信息添加进去，返回数据
        paper2.setStudent(student);
        //获取考场信息，将考场信息放入student_paper
        ExaminationRoom examinationRoom = studentExaminationDao.findOneByPaperId(paperId);
        paper2.setRoomId(examinationRoom.getId());
        //将数据写入数据库
        studentExaminationDao.addStudentPaper(paper2);
        return paper2;
    }

    //对单选题，多选题，判断题进行评分,返回的是试卷的总分数
    @Override
    public int createScore(Integer stuId, String roomId) {
        JSONObject jsonObject = studentExaminationDao.createScore(stuId, roomId);
        PaperQues paperQues = JSONObject.toJavaObject(jsonObject, PaperQues.class);
        //对选择题进行评分
        List<QuesSingleChoose> quesSingleChooses = paperQues.getQuesSingleChooses();
        int singleChoosesScore=0;
        for (int i = 0; i < quesSingleChooses.size(); i++) {
            QuesSingleChoose quesSingleChoose = quesSingleChooses.get(i);
            int score = markingTestPapers.markingSingleChoice(quesSingleChoose.getAnswer(), quesSingleChoose.getStudentAnswer(), quesSingleChoose.getQuesScore());
            singleChoosesScore += score;
        }
        //对多选题进评分
        List<QuesMultipleChoose> quesMultipleChooses = paperQues.getQuesMultipleChooses();
        int multipleChoosesScore=0;
        for (int i = 0; i <quesMultipleChooses.size() ; i++) {
            QuesMultipleChoose quesMultipleChoose = quesMultipleChooses.get(i);
            int score = markingTestPapers.markingMultipleChoice(quesMultipleChoose.getAnswer(), quesMultipleChoose.getStudentAnswer(), quesMultipleChoose.getQuesScore());
            multipleChoosesScore += score;
        }
        //对判断题进行评分,
        List<QuesJudge> quesJudges = paperQues.getQuesJudges();
        int judgesScore=0;
        for (int i = 0; i < quesJudges.size(); i++) {
            QuesJudge quesJudge = quesJudges.get(i);
            int score = markingTestPapers.markingJudge(quesJudge.getAnswer(), quesJudge.getStudentAnswer(), quesJudge.getQuesScore());
            judgesScore += score;

        }
        return singleChoosesScore+multipleChoosesScore+judgesScore;
    }


}
