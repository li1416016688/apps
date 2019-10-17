/**
 * 临时存储随机试卷
 * 相比于Paper实体类多了四种题目的数量信息
 * 不包含id，在试卷写入数据库后废弃
 * 题目数量的List中，其顺序于数据库中的q_question_type表中的question_type一一对应
 * 所有的题目放在一个List中，对应的分数放在另一个List中，两者一一对应
 */
package com.easyexam.apps.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RandomPaper {
    private Integer level;  //整张试卷的难度
    private int subjectId;  //学科的id
    private List<Integer> questionCount;  //题目的数量，针对题型而言
    private List<Object> questions;     //试卷问题
    private List<Integer> quesScores;   //试卷分数，记录了每一题的分数
}
