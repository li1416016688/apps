package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.QuestionManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionManageController {


    @Autowired
    private QuestionManageService questionManageService;

    @Autowired
    private CodeMsg codeMsg;

    @RequestMapping("/singleChoose")
    public String singleChoose() {
        return "questUpdate";
    }

    @RequestMapping("/singleChooseList.do")
    @ResponseBody
    public Map listQuesChoose(Integer page, Integer limit, Integer subjectId, Integer quesId, String questionInfo) {


        HashMap<String, Object> map = new HashMap<>();

        if (quesId == null) {
            quesId = 1;
        }
        if (quesId == 2) {
            List<QuesMultipleChoose> list = questionManageService.finAllQuesMultipleChooses(subjectId, questionInfo, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else if (quesId == 3) {
            List<QuesJudge> list = questionManageService.findAllQuesJudges(subjectId, questionInfo, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else if (quesId == 4) {
            List<QuesQuestionsAnswers> list = questionManageService.findAllQuesQuestionsAnswers(subjectId, questionInfo, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else {
            List<QuesSingleChoose> list = questionManageService.findAllQuesSingleChooses(subjectId, questionInfo,page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        }

        map.put("code", 0);
        return map;

    }

    @RequestMapping("/deleteQues.do")
    @ResponseBody
    public JsonResult deleteQuestById(Integer quesId, Integer id) {
        if (quesId == null || id == null || quesId < 1 || quesId > 4) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
        if (quesId == 2) {
            questionManageService.deleteMultipleChooseById(id);
        } else if (quesId == 3) {
            questionManageService.deleteQuesJudgeById(id);
        } else if (quesId == 4) {
            questionManageService.deleteQuestionsAnswerById(id);
        } else if (quesId == 1) {
            questionManageService.deleteSingleChooseById(id);
        }

        return new JsonResult(ErrorCode.DELETE_QUESTION_SUCCESS, codeMsg.getDeleteQuesSuccess());
    }

    @RequestMapping("/updateQuesSingleChoose.do")
    @ResponseBody
    public JsonResult updateQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        questionManageService.updateQuestionById(quesSingleChoose,1);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesSuccess());
    }

    @RequestMapping("/updateQuesMultipleChoose.do")
    @ResponseBody
    public JsonResult updateQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        questionManageService.updateQuestionById(quesMultipleChoose,2);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("/updateQuesJudge.do")
    @ResponseBody
    public JsonResult updateQuesJudge(QuesJudge quesJudge) {
        questionManageService.updateQuestionById(quesJudge,3);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("/updateQuesQuestionsAnswers.do")
    @ResponseBody
    public JsonResult updateQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        questionManageService.updateQuestionById(quesQuestionsAnswers,4);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("findQues.do")
    @ResponseBody
    public JsonResult findQuestById(Integer quesId, Integer id) {
        Object quest = questionManageService.findQuestById(id, quesId);
        return new JsonResult(ErrorCode.FIND_QUESTION_SUCCESS, quest);
    }
}
