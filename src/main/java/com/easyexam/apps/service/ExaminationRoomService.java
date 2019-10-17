package com.easyexam.apps.service;

import com.easyexam.apps.entity.ExaminationRoom;

import java.util.List;

public interface ExaminationRoomService {
    public List<ExaminationRoom> findAllExaminationRoom(Integer page, Integer limit);
    public ExaminationRoom findOneExaminationRoom(Integer id);
    public void  updateExaminationRoom(ExaminationRoom room);
    public void deleteExaminationRoom(Integer id);
}
