package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
    private Integer uid;
    private String name;
    private String password;
    private Integer age;
    private String sex;
    private String position;
    private String remark;
    private List<Integer> pid;
    private List<Role> role;

}