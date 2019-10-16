package com.easyexam.apps.dao;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;

import java.util.List;
import java.util.Set;

public interface QuestionManageDao {
    //写入题目的方法（共四种题目类型）
    public int importQuesSingleChoose(List<QuesSingleChoose> quesSingleChoose);
    public int importQuesMultipleChoose(List<QuesMultipleChoose> quesMultipleChoose);
    public int importQuesJudge(List<QuesJudge> quesJudge);
    public int importQuesQuestionsAnswers(List<QuesQuestionsAnswers> quesQuestionsAnswers);

    //查询所有的subjectId
    public Set<Integer> findAllSubjectId();
}
