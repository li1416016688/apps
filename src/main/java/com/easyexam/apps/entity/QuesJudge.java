/**
 * 判断题
 */
package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuesJudge implements Serializable {
    private int id;
    private String question;
    private int answer; //-1为空，0为错，1为对
    private int studentAnswer;//考生答案;-1为空，0为错，1为对
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写

    private Integer quesScore; //每到试题的分数
    private Integer questionType;

}
