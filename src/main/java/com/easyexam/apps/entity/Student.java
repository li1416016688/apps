package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private int id;
    private String name;
    private String idCard;  //身份证
    private String password;
    private String phone;
    private String sex;
    private int candidateNumber;    //考号信息
    private String rdesc;//角色描述
}
