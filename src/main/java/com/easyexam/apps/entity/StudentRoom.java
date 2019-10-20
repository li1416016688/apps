package com.easyexam.apps.entity;


import lombok.Data;

@Data
public class StudentRoom {
    private Integer id;
    private Integer rid;
    private ExaminationRoom examinationRoom;
    private Integer sid;
    private Student student;

}
