package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.StudentManageDao;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.entity.StudentRole;
import com.easyexam.apps.exection.MyException;
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
    public List<Student> findAllExamineeId(){
        List<Student> examineeIdList = studentManageDao.findAllExamineeId();
        return examineeIdList;
    }
    @Override
    public StudentPaper findOneExaminee(Integer id) {
        StudentPaper examinee = studentManageDao.findOneExaminee(id);
        return examinee;
    }

    @Override
    public void updateExaminee(Student student) {
        studentManageDao.updateExaminee(student);
    }

    @Override
    public void addExaminee(Student student) {
        studentManageDao.addExaminee(student);
    }

    @Override
    public void addExamineeRole(StudentRole studentRole) {
        studentManageDao.addExamineeRole(studentRole);
    }


}
