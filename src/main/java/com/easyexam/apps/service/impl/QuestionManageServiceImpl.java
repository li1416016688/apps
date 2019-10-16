package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.QuestionManageService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {

    @Autowired
    private QuestionManageDao questionManageDao;

    @Autowired
    private CodeMsg codeMsg;

    @Override
    public List<QuesSingleChoose> findAllQuesSingleChooses(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesSingleChooses(subjectId, info);
    }

    @Override
    public List<QuesMultipleChoose> finAllQuesMultipleChooses(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.finAllQuesMultipleChooses(subjectId, info);
    }

    @Override
    public List<QuesJudge> findAllQuesJudges(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesJudges(subjectId, info);
    }

    @Override
    public List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesQuestionsAnswers(subjectId, info);
    }

    @Override
    public void deleteSingleChooseById(Integer id) {
        int i = questionManageDao.deleteSingleChooseById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void deleteMultipleChooseById(Integer id) {
        int i = questionManageDao.deleteMultipleChooseById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void deleteQuesJudgeById(Integer id) {
        int i = questionManageDao.deleteQuesJudgeById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void deleteQuestionsAnswerById(Integer id) {
        int i = questionManageDao.deleteQuestionsAnswerById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void updateQuestionById(Object e, Integer quesId) {
        int i = 0;
        if (quesId == 1) {
            QuesSingleChoose quesSingleChoose = (QuesSingleChoose) e;
            i = questionManageDao.updateSingleChooseById(quesSingleChoose);
        } else if (quesId == 3) {
            QuesMultipleChoose quesMultipleChoose = (QuesMultipleChoose) e;
            i = questionManageDao.updateMultipleChooseById(quesMultipleChoose);
        } else if (quesId == 4) {
            QuesJudge quesJudge = (QuesJudge) e;
            i = questionManageDao.updateQuesJudgeById(quesJudge);
        } else if (quesId == 2) {
            QuesQuestionsAnswers quesQuestionsAnswers = (QuesQuestionsAnswers) e;
            i = questionManageDao.updateQuestionsAnswerById(quesQuestionsAnswers);
        }
        if (i <= 0) {
            throw new MyException(ErrorCode.UPDATE_QUESTION_FAIL, codeMsg.getUpdateQuesFail());
        }

    }

    @Override
    public Object findQuestById(Integer id, Integer quesId) {
        if (quesId == null || id == null || quesId < 1 || quesId > 4) {
            throw new MyException(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
        }

        Object quest = null;
        if (quesId == 2) {
            quest = questionManageDao.findQuesSingleChooseById(id);
            return quest;
        } else if (quesId == 3) {
            quest = questionManageDao.findQuesMultipleChooseById(id);
            return quest;
        } else if (quesId == 4) {
            quest = questionManageDao.findQuesJudgeById(id);
            return quest;
        } else if (quesId == 1) {
            quest = questionManageDao.findQuestionsAnswerById(id);
            return quest;
        }
        if (quest == null) {
            throw new MyException(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
        }
        return new JsonResult(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
    }
}
