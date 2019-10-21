package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.StudentManageDao;
import com.easyexam.apps.entity.Paper;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.entity.StudentRole;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentExaminationService;
import com.easyexam.apps.service.ExaminationRoomService;
import com.easyexam.apps.service.StudentManageService;
import com.github.pagehelper.PageHelper;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class StudentManageServiceImpl implements StudentManageService {
    @Autowired
    private StudentManageDao studentManageDao;
    @Autowired
    private CodeMsg codeMsg;

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
    @Override
    public void addExamineeJoinExam(String beginTime,String endTime,String roomName, Integer sid,Integer rid) {

        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newTime = formatter.format(date);


        try {
            Date newTime2 = formatter.parse(newTime);
            System.out.println(newTime2);
            Date beginTime2 = formatter.parse(beginTime);
            System.out.println(beginTime2);

            Date endTime2 = formatter.parse(endTime);
            System.out.println(endTime2);


            if (beginTime2.getTime()>=endTime2.getTime()&&beginTime2.getTime()<=newTime2.getTime()){
                throw new MyException(ErrorCode.ADD_EXAMINEE_TIME_ERROR,codeMsg.getAddExamineeTimeError());
            }
            Integer examinationSiteId = studentManageDao.findExaminationSite(beginTime,endTime,roomName);
            rid=examinationSiteId;
            if (rid==null||"".equals(rid)){
                throw new MyException(ErrorCode.ADD_EXAMINEESTEPERROR,codeMsg.getAddExamineeStepError());
            }
            ExaminationRoom peopleNum = studentManageDao.findExaminationPeopleNum(rid);
            int totalPeopleNum = peopleNum.getTotalPeopleNum();
            int joinPeopleNum = peopleNum.getJoinPeopleNum();
            if (joinPeopleNum>=totalPeopleNum){
                throw new MyException(ErrorCode.ADD_EXAMINEE_OVER_PEOPLE_NUM_ERRPR,codeMsg.getAddExamineeOverPeopleNumError());
            }
            System.out.println(rid);
            System.out.println(sid);
            List<StudentRoom> examinationId = studentManageDao.findExaminationId(sid);
            Integer roomId =null;
            for(StudentRoom studentRoom:examinationId){
                roomId = studentRoom.getRid();
                if (roomId.equals(rid)){
                    throw new MyException(ErrorCode.ADD_EXAMINATION_ID_OVER_ERROR,codeMsg.getAddExaminationIdOverError());
                }
            }

            studentManageDao.addExamineeJoinExam(rid,sid);

            studentManageDao.updateJoinExamNum(joinPeopleNum,rid);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }


}
