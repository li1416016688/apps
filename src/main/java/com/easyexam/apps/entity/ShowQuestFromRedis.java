package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class ShowQuestFromRedis {
    private Integer questionId;
    private String question;
    private Integer level;
    private String tag;
    private String questionType;

}
