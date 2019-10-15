package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.service.QuestionManageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {

    @Autowired
    private QuestionManageDao questionManageDao;

    @Override
    public List<QuesSingleChoose> findAllQuesSingleChooses(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesSingleChooses(subjectId, info);
    }

    @Override
    public List<QuesMultipleChoose> finAllQuesMultipleChooses(Integer subjectId,  String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.finAllQuesMultipleChooses(subjectId, info);
    }

    @Override
    public List<QuesJudge> findAllQuesJudges(Integer subjectId,  String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesJudges(subjectId, info);
    }

    @Override
    public List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(Integer subjectId,  String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesQuestionsAnswers(subjectId, info);
    }
}
