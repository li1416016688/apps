package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Complexity implements Serializable {

    private Integer id;//1,一般   2，中等   3，困难
    private String name;
}
