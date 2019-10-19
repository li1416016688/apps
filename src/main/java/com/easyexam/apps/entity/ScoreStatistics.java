package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class ScoreStatistics {
    private Integer subjectId;
    private String max;
    private Integer totalScore;
    private Integer averageScore;
}
