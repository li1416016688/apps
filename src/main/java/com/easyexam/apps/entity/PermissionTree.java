package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionTree implements Serializable {
    private Integer pid;
    private String name;
    private List<PermissionTree> children;
}
