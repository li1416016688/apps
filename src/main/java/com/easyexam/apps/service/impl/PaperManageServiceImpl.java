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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Autowired
    CodeMsg codeMsg;
    @Autowired(required = false)
    PaperManageDao paperManageDao;
    @Autowired
    RandomQues randomQues;

    @Override
    public JsonResult createRandomPaper(RandomPaper randomPaper,int makeId) {
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
        String name = randomPaper.getName();
        int subjectId = randomPaper.getSubjectId();
        Integer level = randomPaper.getLevel();
        HashMap paperInfo = new HashMap<>();
        paperInfo.put("name",name);
        paperInfo.put("subjectId",subjectId);
        paperInfo.put("makeId",makeId);
        paperInfo.put("level",level);
        int insertNum = paperManageDao.createPaper(paperInfo);  //返回插入的行数
        Integer paperId = (Integer) paperInfo.get("id");        //返回插入的试卷id
        if(insertNum != 1){
            return new JsonResult(ErrorCode.SERVER_ERROR,codeMsg.getServerError());
        }
        //开始写入单选题
        HashMap quesInfo = new HashMap<>();
        quesInfo.put("paperId",paperId);
        quesInfo.put("questionId",singleChooseIds);
        quesInfo.put("questionType",1);
        paperManageDao.insertQuesIntoPaper(quesInfo);
        //开始写入多选题
        quesInfo.put("paperId",paperId);
        quesInfo.put("questionId",multipleChooseIds);
        quesInfo.put("questionType",2);
        paperManageDao.insertQuesIntoPaper(quesInfo);
        //开始写入判断题
        quesInfo.put("paperId",paperId);
        quesInfo.put("questionId",judgeIds);
        quesInfo.put("questionType",3);
        paperManageDao.insertQuesIntoPaper(quesInfo);
        //开始写入问答题
        quesInfo.put("paperId",paperId);
        quesInfo.put("questionId",questionsAnswers);
        quesInfo.put("questionType",4);
        paperManageDao.insertQuesIntoPaper(quesInfo);

        //ToDo:如果没意外应该写一个新的接口；用于给试卷设定分数
        // 不然卷子将使用默认分数1分;
        // 新的接口完成后，这个接口直接跳转新接口，强制设定分数；

        return new JsonResult(ErrorCode.CREATE_PAPER_SUCCESS,codeMsg.getCreatePaperSuccess());
    }

    @Override
    public JsonResult deletePaper(int id) {
        int i = paperManageDao.deletePaper(id);
        if(i != 1){
            return new JsonResult(ErrorCode.SERVER_ERROR,codeMsg.getServerError());
        }else{
            return new JsonResult(ErrorCode.DELETE_PAPER_SUCCESS,codeMsg.getDeletePaperSuccess());
        }
    }
}

