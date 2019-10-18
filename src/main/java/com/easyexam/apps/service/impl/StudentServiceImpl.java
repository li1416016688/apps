package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.StudentDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentService;
import com.easyexam.apps.utils.CreateRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
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


        //按照规定随机生成对应类型的试题
        List<Object> singleChooses = CreateRandom.getList(allSingleChoose, num1);
        List<Object> multipleChooses = CreateRandom.getList(allMultipleChoose, num2);
        List<Object> judges = CreateRandom.getList(allJudge, num3);
        List<Object> questionsAnswers = CreateRandom.getList(allQuestionsAnswers, num4);


        //存储的是生成的练习试题
        LinkedHashMap<String, List<Object>> randomTopic = new LinkedHashMap<>();
        randomTopic.put("singleChooses",singleChooses);
        randomTopic.put("multipleChooses",multipleChooses);
        randomTopic.put("judges",judges);
        randomTopic.put("questionsAnswers",questionsAnswers);

        return randomTopic;
    }




}
