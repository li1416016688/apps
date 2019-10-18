package com.easyexam.apps.entity;

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
    private String studentAnswer;//考生答案
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写

    private Integer quesScore; //每到试题的分数


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChooseA() {
        return chooseA;
    }

    public void setChooseA(String chooseA) {
        this.chooseA = chooseA;
    }

    public String getChooseB() {
        return chooseB;
    }

    public void setChooseB(String chooseB) {
        this.chooseB = chooseB;
    }

    public String getChooseC() {
        return chooseC;
    }

    public void setChooseC(String chooseC) {
        this.chooseC = chooseC;
    }

    public String getChooseD() {
        return chooseD;
    }

    public void setChooseD(String chooseD) {
        this.chooseD = chooseD;
    }

    public String getChooseE() {
        return chooseE;
    }

    public void setChooseE(String chooseE) {
        this.chooseE = chooseE;
    }

    public String getChooseF() {
        return chooseF;
    }

    public void setChooseF(String chooseF) {
        this.chooseF = chooseF;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
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
