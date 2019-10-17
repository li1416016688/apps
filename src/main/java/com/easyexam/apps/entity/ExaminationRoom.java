/**
 * 考场信息，在很多地方用room表示
 */
package com.easyexam.apps.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ExaminationRoom {
    private int id;
    private String roomName;    //考场名称
    private int joinPeopleNum;  //本次考试实际参与人数
    private int totalPeopleNum; //本次考试参与人数上限
    private String address; //考场地址
    private int subjectId;  //对应的科目id
    private Subject subject;
    private int invigilateId;   //监考人id，对应User表中的主键id
    private User invigilate;
    private Date beginTime;
    private Date endTime;
    private int paperId;    //试卷id
    private Paper paper;

}
