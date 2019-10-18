package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRole implements Serializable {
    private Integer id;
    private Integer uid;
    private List<Integer> rid;
    private List<Role> role;

}
