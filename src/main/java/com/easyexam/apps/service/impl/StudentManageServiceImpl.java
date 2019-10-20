package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.StudentManageDao;
import com.easyexam.apps.entity.Paper;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.entity.StudentRole;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentExaminationService;
import com.easyexam.apps.service.StudentManageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentManageServiceImpl implements StudentManageService {
    @Autowired
    private StudentManageDao studentManageDao;

    @Autowired
    private StudentExaminationService studentExaminationService;
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

    @Override
    public LinkedHashMap<String, List<Object>> showScore(Integer subjectId, Integer paperId) {

        List<StudentPaper> studentPaperList = studentManageDao.findStuIdAndRoomId(subjectId, paperId);

        if (studentPaperList.size() == 0) {
            throw new MyException(0, "没有此试卷");
        }
        LinkedHashMap<String, List<Object>> map = new LinkedHashMap<>();

        int i = 0;

        List<String> userList = new ArrayList<>();

        for (StudentPaper studentPaper : studentPaperList) {
            LinkedList<Object> list = studentExaminationService.createScore(studentPaper.getStuId(), String.valueOf(studentPaper.getRoomId()));
            userList.add("user" + 1);
            map.put("user" + i++, list);
        }


        return map;
    }

    @Override
    public Paper findPaperById(Integer id) {
        return null;
    }

}
