package com.easyexam.apps.service;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;

import java.util.List;

public interface QuestionManageService {

    List<QuesSingleChoose> findAllQuesSingleChooses(Integer subjectId,  String info, Integer page, Integer limit);

    List<QuesMultipleChoose> finAllQuesMultipleChooses(Integer subjectId,  String info, Integer page, Integer limit);

    List<QuesJudge> findAllQuesJudges(Integer subjectId,  String info, Integer page, Integer limit);

    List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(Integer subjectId, String info, Integer page, Integer limit);
}