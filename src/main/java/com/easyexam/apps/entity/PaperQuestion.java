package com.easyexam.apps.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class PaperQuestion implements Serializable {

    private Integer paperId;
    private Integer questionId;
    private Integer questionType;
    private Integer score;
    private Integer id;

}
