package com.easyexam.apps.dao;

import com.easyexam.apps.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentManageDao {
    //查找所有的考生有分页
    public List<Student> findAllExaminee();

    //查找所有的考生，作为业务判断的使用的
    public List<Student> findAllExamineeId();
    //查找一个考生的信息
    public StudentPaper findOneExaminee(Integer id);
    //修改考生信息
    public  void  updateExaminee(Student student);

    //增加一个考生的信息
    public void addExaminee(Student student);
    //增加考生的角色
    public void addExamineeRole(StudentRole studentRole);
    //查找考场的场次
    public Integer findExaminationSite(String beginTime,String endTime,String roomName);
    //学生加入考试
    public void addExamineeJoinExam(@Param("rid") Integer rid, @Param("sid") Integer sid);
    //查找学生加入人数
    public ExaminationRoom findExaminationPeopleNum(Integer id);

    //加入人数增加
    public void updateJoinExamNum(@Param("joinPeopleNum") Integer joinPeopleNum,@Param("id") Integer id);
    //查找考场id
    public List<StudentRoom> findExaminationId(Integer id);

}
