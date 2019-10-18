package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.PaperManageDao;
import com.easyexam.apps.pojo.RandomPaper;
import com.easyexam.apps.service.PaperManageService;
import com.easyexam.apps.utils.RandomQues;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Autowired
    CodeMsg codeMsg;
    @Autowired(required = false)
    PaperManageDao paperManageDao;
    @Autowired
    RandomQues randomQues;

    @Override
    public JsonResult createRandomPaper(RandomPaper randomPaper) {
        List<Integer> questionCount = randomPaper.getQuestionCount();

        //数据有效性验证
        if(randomPaper.getLevel() == null || questionCount == null){
            return new JsonResult(ErrorCode.CREATE_PAPER_PARAM_LOST,codeMsg.getCreatePaperParamLost());
        }

        //调用工具类获取题目，生成包含id信息的List
        List<Integer> singleChooseIds = randomQues.getSingleChoose(randomPaper.getQuestionCount(), randomPaper.getSubjectId(), randomPaper.getLevel());
        List<Integer> multipleChooseIds = randomQues.getMultipleChoose(randomPaper.getQuestionCount(), randomPaper.getSubjectId(), randomPaper.getLevel());
        List<Integer> judgeIds = randomQues.getJudge(randomPaper.getQuestionCount(), randomPaper.getSubjectId(), randomPaper.getLevel());
        List<Integer> questionsAnswers = randomQues.getQuestionsAnswers(randomPaper.getQuestionCount(), randomPaper.getSubjectId(), randomPaper.getLevel());

        //将生成的试卷分别写入数据库

        //将试卷返回前端

        return new JsonResult(2350,singleChooseIds);
    }
}
