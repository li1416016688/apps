package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionType implements Serializable {

    private Integer id;
    private Integer questionType;
    private String questionName;
    private String questionTableName;

}
