package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.dao.StudentDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentService;
import com.easyexam.apps.utils.CreateRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private QuestionManageDao questionManageDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Student studentLogin(String idCard, String password) {
        Student student = studentDao.studentLogin(idCard);
        if (student == null ){
            throw new MyException(ErrorCode.ID_CARD_ERROR,codeMsg.getIdCardError());
        }else if(!(password.equals(student.getPassword()))) {
            throw new MyException(ErrorCode.PASSWORD_ERROR,codeMsg.getPasswordError());
        }else {
            return student;
        }
    }

    @Override
    public Integer studentRegister(Student student) {
        Integer integer = studentDao.studentRegister(student);
        if (integer < 0){
            throw new MyException(ErrorCode.STUDENT_REGISTER_FAIL,codeMsg.getStudentRegisterFail());
        }
        return integer;
    }

    @Override
    public List<Subject> findAllSubject() {
        List<Subject> list = studentDao.findAllSubject();
        return list;
    }

    @Override
    public List<Complexity> findAllComplexity() {
        List<Complexity> allComplexity = studentDao.findAllComplexity();
        return allComplexity;
    }
    @Override
    public Map<String, List<Object>> createPaper(Integer subjectId, Integer level, Integer num1,
                                           Integer num2, Integer num3, Integer num4){


        if (level == null){
            level=2;
        }
        if (num1 == null){
            num1=0;
        }
        if (num2 == null){
            num2=0;
        }
        if (num3 == null){
            num3=0;
        }
        if (num4 == null){
            num4=0;
        }
        //将所有试题存入redis里面，在redis里面随机获取
        Object paperQues1 = redisTemplate.opsForList().leftPop("paperQues");
        if (paperQues1 !=null){
            PaperQues paperQues = (PaperQues) paperQues1;

            //按照规定随机生成对应类型的试题
            List<Object> singleChooses = CreateRandom.getList(paperQues.getQuesSingleChooses(), num1);
            if (num1 > singleChooses.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            List<Object> multipleChooses = CreateRandom.getList(paperQues.getQuesMultipleChooses(), num2);
            if (num2 > multipleChooses.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            List<Object> judges = CreateRandom.getList(paperQues.getQuesJudges(), num3);
            if (num3 > judges.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            List<Object> questionsAnswers = CreateRandom.getList(paperQues.getQuesQuestionsAnswers(), num4);
            if (num4 > questionsAnswers.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            LinkedHashMap<String, List<Object>> randomTopic = new LinkedHashMap<>();
            randomTopic.put("singleChooses",singleChooses);
            randomTopic.put("multipleChooses",multipleChooses);
            randomTopic.put("judges",judges);
            randomTopic.put("questionsAnswers",questionsAnswers);

            return randomTopic;
        }else {
            //按照难度试题类型生产各自的总试题
            //单选题
            List<QuesSingleChoose> allSingleChoose = studentDao.findAllSingleChooseByLevel(subjectId, level);
            if (num1 > allSingleChoose.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }

            //多选题
            List<QuesMultipleChoose> allMultipleChoose = studentDao.findAllMultipleChooseByLevel(subjectId, level);
            if (num2 > allMultipleChoose.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            //判断题
            List<QuesJudge> allJudge = studentDao.findAllJudgeByLevel(subjectId, level);
            if (num3 > allJudge.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }
            //简答题
            List<QuesQuestionsAnswers> allQuestionsAnswers = studentDao.findAllQuestionsAnswers(subjectId, level);
            if (num4 > allQuestionsAnswers.size()){
                throw new MyException(ErrorCode.TOPIC_NOT_ENOUGH,codeMsg.getTopicNotEnough());
            }

            //存试题库所有的试题，并放进redis里面
            PaperQues paperQues = new PaperQues();
            paperQues.setQuesSingleChooses(allSingleChoose);
            paperQues.setQuesMultipleChooses(allMultipleChoose);
            paperQues.setQuesJudges(allJudge);
            paperQues.setQuesQuestionsAnswers(allQuestionsAnswers);
            redisTemplate.opsForList().leftPush("paperQues",paperQues);


            //按照规定随机生成对应类型的试题
            List<Object> singleChooses = CreateRandom.getList(allSingleChoose, num1);
            List<Object> multipleChooses = CreateRandom.getList(allMultipleChoose, num2);
            List<Object> judges = CreateRandom.getList(allJudge, num3);
            List<Object> questionsAnswers = CreateRandom.getList(allQuestionsAnswers, num4);

            LinkedHashMap<String, List<Object>> randomTopic = new LinkedHashMap<>();
            randomTopic.put("singleChooses",singleChooses);
            randomTopic.put("multipleChooses",multipleChooses);
            randomTopic.put("judges",judges);
            randomTopic.put("questionsAnswers",questionsAnswers);

            return randomTopic;
        }


    }

    @Override
    public Map<String, Object> findSubjectScore() {
        List<ScoreStatistics> statisticsList = studentDao.findSubjectScore();
        List<Subject> subjectList = questionManageDao.findSubject();

        List<String> list = new ArrayList<>();
        for (Subject subject : subjectList) {
            list.add(subject.getName());
        }

        List<Integer> maxList = new ArrayList<>();
        for (ScoreStatistics statistics : statisticsList) {
            maxList.add(statistics.getMax());
        }

        List<Integer> auxList = new ArrayList<>();
        for (ScoreStatistics scoreStatistics : statisticsList) {
            auxList.add(scoreStatistics.getAverageScore());
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("maxList", maxList);
        map.put("auxList", auxList);

        System.out.println(map);

        return map;
    }


}
