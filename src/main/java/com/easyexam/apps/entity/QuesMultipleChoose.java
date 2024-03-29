package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuesMultipleChoose implements Serializable {
    private int id;
    private String question;
    private String chooseA;
    private String chooseB;
    private String chooseC;
    private String chooseD;
    private String chooseE;
    private String chooseF;
    private String answer;
    private String studentAnswer;//考生答案
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写

    private Integer quesScore; //每到试题的分数
    private Integer questionType;

}
