package com.easyexam.apps.entity;

import lombok.Data;

import java.util.List;

@Data
public class StudentPaper {
    private int id;
    private Integer stuId;
    private Student student;
    private Integer roomId;
    private ExaminationRoom room;
    private int score;
    private PaperQues paperQues;
}
