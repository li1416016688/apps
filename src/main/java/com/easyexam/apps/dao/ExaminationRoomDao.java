package com.easyexam.apps.dao;

import com.easyexam.apps.entity.ExaminationRoom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRoomDao {
    public List<ExaminationRoom>findAllExaminationRoom();

}
