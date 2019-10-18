package com.easyexam.apps.service;

import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.entity.StudentPaper;

import java.util.List;

public interface StudentExaminationService {

    public List<ExaminationRoom> findAllExaminationRoom(Integer subjectId,String roomName);

    //根据student的idCard,和paperId，查找对应的试卷
    public StudentPaper createPaper(Integer paperId,String idCard);
}
