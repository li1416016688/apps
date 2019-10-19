package com.easyexam.apps.dao;

import com.easyexam.apps.entity.Paper;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.entity.StudentRole;
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



}
