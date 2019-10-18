package com.easyexam.apps.dao;

import com.easyexam.apps.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao {
    //student登录
    public Student studentLogin(String idCard);
    //student注册
    public Integer studentRegister(Student student);
    //展示所有考试科目
    public List<Subject> findAllSubject();
    //展示所有难易程度
    public List<Complexity> findAllComplexity();

    //传入level的id返回单选题对应难度的题
    public List<QuesSingleChoose> findAllSingleChooseByLevel(@Param("subjectId") Integer subjectId, @Param("level") Integer level);
    //传入level的id返回多选题对应难度的题
    public List<QuesMultipleChoose> findAllMultipleChooseByLevel(@Param("subjectId") Integer subjectId, @Param("level") Integer level);
    //传入level的id返回判断题对应难度的题
    public List<QuesJudge> findAllJudgeByLevel(@Param("subjectId") Integer subjectId, @Param("level") Integer level);
    //传入level的id返回简答题对应难度的题
    public List<QuesQuestionsAnswers> findAllQuestionsAnswers(@Param("subjectId") Integer subjectId, @Param("level") Integer level);

    //分别对于4种题型，通过id查询
    public QuesSingleChoose findQuesSingleChooseById(Integer id);
    public QuesMultipleChoose findQuesMultipleChooseById(Integer id);
    public QuesJudge findQuesJudgeById(Integer id);
    public QuesQuestionsAnswers findQuesQuestionsAnswersById(Integer id);

}
