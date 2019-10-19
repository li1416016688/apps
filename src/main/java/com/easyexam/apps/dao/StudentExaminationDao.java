package com.easyexam.apps.dao;

import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.entity.PaperQuestion;
import com.easyexam.apps.entity.StudentPaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//前台学生考试
@Repository
public interface StudentExaminationDao {

    //根据学科的id展示对应的考场
    public List<ExaminationRoom> findAllExaminationRoom(@Param("subjectId") Integer subjectId, @Param("roomName") String roomName);

    // 传入paper的id,查到这一套试卷，题类型的id,题类型确定以后具体的id,分数
    public List<PaperQuestion> createPaper(Integer paperId);

    //通过paperId查询考场信息
    public ExaminationRoom findOneByPaperId(Integer paperId);
    //将前台数据存在subject_paper表的paper_desc
    public void addStudentPaper(StudentPaper studentPaper);



}
