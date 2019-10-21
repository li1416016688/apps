package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleTree implements Serializable {
    private Integer rid;
    private String name;
    private List<RoleTree> children;
}
