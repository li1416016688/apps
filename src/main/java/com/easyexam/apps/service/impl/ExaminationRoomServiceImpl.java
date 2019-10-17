package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.ExaminationRoomDao;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
//        PageInfo <ExaminationRoom>pageInfo = new PageInfo<>(examinationRoomList);
//        List<ExaminationRoom> roomList = pageInfo.getList();

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
}
