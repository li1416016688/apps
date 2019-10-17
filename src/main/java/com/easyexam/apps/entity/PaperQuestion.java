package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaperQuestion implements Serializable {

    private Integer paperId;
    private Integer questionId;
    private Integer questionType;
    private Integer score;
    private Integer id;

}
