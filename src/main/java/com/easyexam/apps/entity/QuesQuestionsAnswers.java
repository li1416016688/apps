/**
 * Q&A型问题，问答题
 */
package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class QuesQuestionsAnswers {
    private int id;
    private String question;
    private String answer;
    private int subjectId;
    private Subject subject;
    private int level;
    private String tag;     //标签，目前用户自行填写

    private Integer quesScore; //每到试题的分数


    @Override
    public String toString() {
        return "QuesQuestionsAnswers{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", subject=" + subject +
                ", level=" + level +
                ", tag='" + tag + '\'' +
                '}';
    }
}
