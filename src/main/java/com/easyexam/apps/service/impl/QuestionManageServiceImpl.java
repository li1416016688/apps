package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.service.QuestionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class QuestionManageServiceImpl implements QuestionManageService {
    @Autowired
    private QuestionManageDao questionManageDao;
    @Override
    public List<QuesSingleChoose> findAllQuesSingleChoose() {
        List<QuesSingleChoose> SingleChooselist = questionManageDao.findAllQuesSingleChoose();

        return SingleChooselist;
    }

    @Override
    public List<QuesMultipleChoose> findAllQuesMultipleChoose() {
        List<QuesMultipleChoose> MultipleChoose = questionManageDao.findAllQuesMultipleChoose();
        return MultipleChoose;
    }

    @Override
    public List<QuesJudge> findAllQuesJudge() {
        List<QuesJudge> Judge = questionManageDao.findAllQuesJudge();
        return Judge;
    }

    @Override
    public List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers() {

        List<QuesQuestionsAnswers> QuestionsAnswers = questionManageDao.findAllQuesQuestionsAnswers();
        return QuestionsAnswers;
    }
}
