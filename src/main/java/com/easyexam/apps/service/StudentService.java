package com.easyexam.apps.service;

import com.easyexam.apps.entity.Complexity;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.Subject;

import java.util.List;
import java.util.Map;

public interface StudentService {
    public Student studentLogin(String idCard, String password);
    public Integer studentRegister(Student student);
    public List<Subject> findAllSubject();
    public List<Complexity> findAllComplexity();

    //根据输入的科目和难易程度，生成单选题，多选题，判断题，简答题试卷，
    //生成一套模拟试卷，将试卷放在
    public Map<String, List<Object>> createPaper(Integer subjectId, Integer level, Integer num1,
                                                 Integer num2, Integer num3, Integer num4);

//    //根据题类型id和科目id和具体某一个题的id查询具体的答案
//    public Object findTopic(Integer subjectId, Integer id);

    Map<String, Object> findSubjectScore();

}
