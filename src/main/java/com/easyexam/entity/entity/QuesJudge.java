/**
 * 判断题
 */
package com.easyexam.entity.entity;

import lombok.Data;

@Data
public class QuesJudge {
    private int id;
    private String question;
    private boolean answer; //数据库中1为true；0为false；可以自动注入
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写
}
