package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.ExaminationRoomDao;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExaminationRoomServiceImpl implements ExaminationRoomService {
    @Autowired
    private ExaminationRoomDao examinationRoomDao;

    @Override
    public List<ExaminationRoom> findAllExaminationRoom() {
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
}
