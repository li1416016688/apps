package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentPaper implements Serializable {
    private int id;
    private Integer stuId;
    private Student student;
    private Integer roomId;
    private ExaminationRoom room;
    private int score;
    private com.alibaba.fastjson.JSONObject paperQues;
}
