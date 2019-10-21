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
    public void updateExaminationRoom(ExaminationRoom room ,String subjectName,String paperName,String invigilateName) {

        String roomName = room.getRoomName();
        List<ExaminationRoom> allExaminationRoomId = examinationRoomDao.findAllExaminationRoomId();
        for (ExaminationRoom Room:allExaminationRoomId){
            String room1 = Room.getRoomName();

            if (room1.equals(roomName)){
                throw new MyException(ErrorCode.ADD_ROOM_NAME_REPEAT,codeMsg.getAddRoomNameRepeat());

            }
        }
        int joinPeopleNum = room.getJoinPeopleNum();
        int totalPeopleNum = room.getTotalPeopleNum();
        if (joinPeopleNum>totalPeopleNum){
            throw new MyException(ErrorCode.ADD_ROOM_NUM_LESS,codeMsg.getAddRoomNumLess());
        }

        if (invigilateName==""||invigilateName==null){
            throw new MyException(ErrorCode.ADD_USER_PAGE_NULL,codeMsg.getAddUserPageNull());
        }

        User user = examinationRoomDao.findInvigilateId(invigilateName);
        Integer invigilateId = user.getUid();


        if (subjectName == ""||subjectName==null) {
            throw new MyException(ErrorCode.ADD_SUBJECT_PAGE_NULL,codeMsg.getAddSubjectPageNull());
        }

        Subject subject = examinationRoomDao.findSubjectId(subjectName);
        int subjectId = subject.getId();


        if (paperName==""||paperName==null){
            throw  new MyException(ErrorCode.ADD_PAPER_PAGE_NULL,codeMsg.getAddPaperPageNull());
        }

        Paper paper = examinationRoomDao.findPaperId(paperName);
        int paperId = paper.getId();



        if ("".equals(subjectId)&&"".equals(invigilateId)&&"".equals(paperId)){
            throw new MyException(ErrorCode.ADD_USER_PAPER_SUJECT_ID__NULL,codeMsg.getAddUserPaperSujectIdNull());
        }
        room.setSubjectId(subjectId);
        room.setInvigilateId(invigilateId);
        room.setPaperId(paperId);

        examinationRoomDao.updateExaminationRoom(room);
    }

    @Override
    public void deleteExaminationRoom(Integer id) {
        examinationRoomDao.deleteExaminationRoom(id);
    }


    @Override
    public void addExaminationRoom(ExaminationRoom room,String subjectName,String paperName,String invigilateName) {
        String roomName = room.getRoomName();
        List<ExaminationRoom> allExaminationRoomId = examinationRoomDao.findAllExaminationRoomId();
        for (ExaminationRoom Room:allExaminationRoomId){
            String room1 = Room.getRoomName();
            if (room1.equals(roomName)){
                throw new MyException(ErrorCode.ADD_ROOM_NAME_REPEAT,codeMsg.getAddRoomNameRepeat());
            }
        }
        int id = room.getId();
        Integer stuNum = examinationRoomDao.ExamineeNumOneRoom(id);
        int totalPeopleNum = room.getTotalPeopleNum();
        if (stuNum>totalPeopleNum){
            throw new MyException(ErrorCode.ADD_ROOM_NUM_OVERSTEP,codeMsg.getAddRoomNumOverstep());
        }

        if (invigilateName==""||invigilateName==null){
            throw new MyException(ErrorCode.ADD_USER_PAGE_NULL,codeMsg.getAddUserPageNull());
        }

        User user = examinationRoomDao.findInvigilateId(invigilateName);
        Integer invigilateId = user.getUid();


        if (subjectName == ""||subjectName==null) {
            throw new MyException(ErrorCode.ADD_SUBJECT_PAGE_NULL,codeMsg.getAddSubjectPageNull());
        }

        Subject subject = examinationRoomDao.findSubjectId(subjectName);
        int subjectId = subject.getId();


        if (paperName==""||paperName==null){
            throw  new MyException(ErrorCode.ADD_PAPER_PAGE_NULL,codeMsg.getAddPaperPageNull());
        }

        Paper paper = examinationRoomDao.findPaperId(paperName);
        int paperId = paper.getId();



        if ("".equals(subjectId)&&"".equals(invigilateId)&&"".equals(paperId)){
            throw new MyException(ErrorCode.ADD_USER_PAPER_SUJECT_ID__NULL,codeMsg.getAddUserPaperSujectIdNull());
        }
        room.setJoinPeopleNum(stuNum);
        room.setSubjectId(subjectId);
        room.setInvigilateId(invigilateId);
        room.setPaperId(paperId);
        examinationRoomDao.addExaminationRoom(room);

    }


    @Override
    public List<ExaminationRoom> findAllExaminationRoomId() {
        return examinationRoomDao.findAllExaminationRoomId();
    }
}
