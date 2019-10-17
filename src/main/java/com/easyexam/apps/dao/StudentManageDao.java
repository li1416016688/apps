package com.easyexam.apps.dao;

import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentManageDao {

    public List<Student> findAllExaminee();

    public StudentPaper findOneExaminee(Integer id);

//    public


}
