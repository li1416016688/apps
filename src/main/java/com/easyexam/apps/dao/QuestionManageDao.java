package com.easyexam.apps.dao;

import com.easyexam.apps.entity.*;

import java.util.List;

public interface QuestionManageDao {
    //写入题目的方法（共四种题目类型）
    public int importQuesSingleChoose(List<QuesSingleChoose> quesSingleChoose);
    public int importQuesMultipleChoose(List<QuesMultipleChoose> quesMultipleChoose);
    public int importQuesJudge(List<QuesJudge> quesJudge);
    public int importQuesQuestionsAnswers(List<QuesQuestionsAnswers> quesQuestionsAnswers);
}
