package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.StudentManageDao;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.service.StudentManageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentManageServiceImpl implements StudentManageService {
@Autowired
private StudentManageDao studentManageDao;

    @Override
    public List<Student> findAllExaminee(Integer page, Integer limit){
        PageHelper.startPage(page, limit);
        List<Student> examineeList = studentManageDao.findAllExaminee();
        return examineeList;
    }

    @Override
    public StudentPaper findOneExaminee(Integer id) {
        StudentPaper examinee = studentManageDao.findOneExaminee(id);
        return examinee;
    }



}
