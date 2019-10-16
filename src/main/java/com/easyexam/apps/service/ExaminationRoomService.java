package com.easyexam.apps.service;

import com.easyexam.apps.dao.ExaminationRoomDao;
import com.easyexam.apps.entity.ExaminationRoom;

import java.util.List;

public interface ExaminationRoomService {

    public List<ExaminationRoom> findAllExaminationRoom();

}
