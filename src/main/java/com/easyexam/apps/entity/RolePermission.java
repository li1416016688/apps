package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RolePermission implements Serializable {
    private Integer id;
    private List<Integer> pid;
    private Integer rid;
    private List<Permission> permission;


}
