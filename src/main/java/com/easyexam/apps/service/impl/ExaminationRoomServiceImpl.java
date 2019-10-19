package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.dao.ExaminationRoomDao;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.entity.Paper;
import com.easyexam.apps.entity.Subject;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.PageHelper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationRoomServiceImpl implements ExaminationRoomService {
    @Autowired
    private ExaminationRoomDao examinationRoomDao;
    @Autowired
    private CodeMsg codeMsg;




    @Override
    public List<ExaminationRoom> findAllExaminationRoom( Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ExaminationRoom> examinationRoomList = examinationRoomDao.findAllExaminationRoom();
        return examinationRoomList;
    }


    @Override
    public ExaminationRoom findOneExaminationRoom(Integer id) {
        ExaminationRoom oneExaminationRoom = examinationRoomDao.findOneExaminationRoom(id);
        return oneExaminationRoom;
    }

    @Override
    public void updateExaminationRoom(ExaminationRoom room) {
        examinationRoomDao.updateExaminationRoom(room);
    }

    @Override
    public void deleteExaminationRoom(Integer id) {
        examinationRoomDao.deleteExaminationRoom(id);
    }

    @Override
    public void addExaminationRoom(ExaminationRoom room,String subjectName,String paperName,String invigilateName) {

        List<Subject> subjectList = examinationRoomDao.findSubject();
        Integer  subId=null;
        String subName=null;
        if (subjectName == ""||subjectName==null) {
            throw new MyException(ErrorCode.ADD_SUBJECT_PAGE_NULL,codeMsg.getAddSubjectPageNull());
        }
        for (Subject subject:subjectList){
            subId=subject.getId();
            subName= subject.getName();
            if (subName != subjectName){
                throw new MyException(ErrorCode.ADD_SUBJECT_SQL_NULL,codeMsg.getAddSubjectSqlNull());
            }
            }
        Subject subject = examinationRoomDao.findSubjectId(subjectName);
        int subjectId = subject.getId();


        List<User> invigilateList = examinationRoomDao.findInvigilate();
        Integer invId=null;
        String invName=null;
        if (invigilateName==""||invigilateName==null){
            throw new MyException(ErrorCode.ADD_USER_PAGE_NULL,codeMsg.getAddUserPageNull());
        }
        for (User user: invigilateList){
            invName=user.getName();
            invId=user.getUid();
            if (invName!=invigilateName){
                throw new MyException(ErrorCode.ADD_USER_SQL_NULL,codeMsg.getAddUserSqlNull());
            }
        }
        User user = examinationRoomDao.findInvigilateId(invigilateName);
        Integer userId = user.getUid();


        List<Paper> paperList = examinationRoomDao.findPaper();
        String papName=null;
        Integer papId=null;
        if (paperName==""||paperName==null){
            throw  new MyException(ErrorCode.ADD_PAPER_PAGE_NULL,codeMsg.getAddPaperPageNull());
        }
        for (Paper paper:paperList){
            papId=paper.getId();
            papName=paper.getName();
            if (papName!=paperName){
                throw new MyException(ErrorCode.ADD_PAPER_SQL_NULL,codeMsg.getAddPaperSqlNull());
            }
        }
        Paper paper = examinationRoomDao.findPaperId(paperName);
        int paperId = paper.getId();

        if ("".equals(subjectId)&&"".equals(userId)&&"".equals(paperId)){
        }
        examinationRoomDao.addExaminationRoom(room);

    }

    @Override
    public List<ExaminationRoom> findAllExaminationRoomId() {
        return examinationRoomDao.findAllExaminationRoomId();
    }
}
