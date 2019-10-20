package com.easyexam.apps.entity;

import lombok.Data;

/**
 * 分数统计实体类
 */
@Data
public class ScoreStatistics {
    private Integer subjectId;
    private Integer max;
    private Integer averageScore;
}
