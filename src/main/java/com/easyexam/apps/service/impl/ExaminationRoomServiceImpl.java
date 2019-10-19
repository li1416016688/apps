package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.ExaminationRoomDao;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationRoomServiceImpl implements ExaminationRoomService {
    @Autowired
    private ExaminationRoomDao examinationRoomDao;

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
    public void addExaminationRoom(ExaminationRoom room) {
        examinationRoomDao.addExaminationRoom(room);

    }

    @Override
    public List<ExaminationRoom> findAllExaminationRoomId() {
        return examinationRoomDao.findAllExaminationRoomId();
    }
}
