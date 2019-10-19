/**
 * 该类主要用于存储学生做过的试卷题目信息
 */
package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaperQues implements Serializable {

    private List<QuesSingleChoose> quesSingleChooses;
    private List<QuesMultipleChoose> quesMultipleChooses;
    private List<QuesJudge> quesJudges;
    private List<QuesQuestionsAnswers> quesQuestionsAnswers;
}
