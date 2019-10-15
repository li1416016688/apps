package com.easyexam.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer uid;
    private String name;
    private String password;
    private Integer age;
    private String sex;
    private String position;
    private String remark;

}
