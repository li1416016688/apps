package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class QuesMultipleChoose {
    private int id;
    private String question;
    private String chooseA;
    private String chooseB;
    private String chooseC;
    private String chooseD;
    private String chooseE;
    private String chooseF;
    private String answer;
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写

    private Integer quesScore; //每到试题的分数

    @Override
    public String toString() {
        return "QuesMultipleChoose{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", chooseA='" + chooseA + '\'' +
                ", chooseB='" + chooseB + '\'' +
                ", chooseC='" + chooseC + '\'' +
                ", chooseD='" + chooseD + '\'' +
                ", chooseE='" + chooseE + '\'' +
                ", chooseF='" + chooseF + '\'' +
                ", answer='" + answer + '\'' +
                ", subject=" + subject +
                ", level=" + level +
                ", tag='" + tag + '\'' +
                '}';
    }
}
