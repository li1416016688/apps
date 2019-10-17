package com.easyexam.apps.service;

import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;

import java.util.List;

public interface StudentManageService {

    public List<Student> findAllExaminee(Integer page, Integer limit);

    public StudentPaper findOneExaminee(Integer id);
}
