package com.easyexam.apps.dao;

import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.entity.Paper;
import com.easyexam.apps.entity.Subject;

import com.easyexam.apps.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExaminationRoomDao {
    public List<ExaminationRoom>findAllExaminationRoom();
    public ExaminationRoom findOneExaminationRoom( Integer id);
    public void  updateExaminationRoom(ExaminationRoom room);
    public void  deleteExaminationRoom(Integer id);
    public void  addExaminationRoom(ExaminationRoom room);
    public List<ExaminationRoom>findAllExaminationRoomId();


    public List<Paper> findPaper();

    public List<Subject> findSubject();

    public List<User> findInvigilate();



    public Paper findPaperId(String  paperName);

    public Subject findSubjectId(String  subjectName);

    public User findInvigilateId(String uname);

}
