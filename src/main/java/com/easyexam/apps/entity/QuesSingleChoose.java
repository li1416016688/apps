package com.easyexam.apps.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuesSingleChoose implements Serializable {
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
    public QuesSingleChoose(){}

    @Override
    public String toString() {
        return "QuesSingleChoose{" +
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
