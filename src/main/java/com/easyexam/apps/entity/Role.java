package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private Integer rid;
    private String rname;
    private String rdesc;
}
