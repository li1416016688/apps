package com.easyexam.apps.entity;

import lombok.Data;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

@Data
public class StudentPaper {
    private int id;
    private Integer stuId;
    private Student student;
    private Integer roomId;
    private ExaminationRoom room;
    private int score;
    private com.alibaba.fastjson.JSONObject paperQues;
}
