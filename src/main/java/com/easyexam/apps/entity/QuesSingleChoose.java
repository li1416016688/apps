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

    public QuesSingleChoose(int id, String question, String chooseA, String chooseB, String chooseC, String chooseD, String chooseE, String chooseF, String answer, int subjectId, Subject subject, int level, String tag) {
        this.id = id;
        this.question = question;
        this.chooseA = chooseA;
        this.chooseB = chooseB;
        this.chooseC = chooseC;
        this.chooseD = chooseD;
        this.chooseE = chooseE;
        this.chooseF = chooseF;
        this.answer = answer;
        this.subjectId = subjectId;
        this.subject = subject;
        this.level = level;
        this.tag = tag;
    }
}
