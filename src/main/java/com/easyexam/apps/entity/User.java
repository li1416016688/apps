package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class User {
    private int uid;
    private String name;
    private String password;
    private String info;
}
