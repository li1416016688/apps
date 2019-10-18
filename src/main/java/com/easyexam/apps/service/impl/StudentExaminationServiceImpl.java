package com.easyexam.apps.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.StudentDao;
import com.easyexam.apps.dao.StudentExaminationDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentExaminationService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;
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
            if (beginTime.compareTo(new Date()) < 0){
                throw new MyException(ErrorCode.EXAM_TIME_STARTED,codeMsg.getExamTimeStarted());
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

        return studentPaper;
    }

//    public void createPaperQueue(Integer paperId, String idCard){
//        //将试卷放进普通消息队列中，
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("paperId",paperId);
//        map.put("idCard",idCard);
//        amqpTemplate.convertAndSend("paper.queue",map);
//    }

}
