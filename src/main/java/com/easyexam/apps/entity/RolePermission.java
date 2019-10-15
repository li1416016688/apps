package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {
    private Integer id;
    private Integer pid;
    private Integer rid;


}
