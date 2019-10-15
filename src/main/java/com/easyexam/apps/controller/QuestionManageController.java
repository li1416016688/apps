package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.service.QuestionManageService;
import com.easyexam.apps.common.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class QuestionManageController {
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private CodeMsg codeMsg;
    @RequestMapping("/QuesSingleChoose/findAll")
    @ResponseBody
    public JsonResult findAllQuesSingleChoose(){
        List<QuesSingleChoose> allQuesSingleChoose = questionManageService.findAllQuesSingleChoose();
        for (QuesSingleChoose singleChoose:allQuesSingleChoose){
            System.out.println(singleChoose);

        }
        return new JsonResult(1011,codeMsg.getSingleChoose());
    }
    @RequestMapping("/QuesMultipleChoose/findAll")
    @ResponseBody
    public JsonResult findAllQuesMultipleChoose(){
        List<QuesMultipleChoose> MultipleChooselist = questionManageService.findAllQuesMultipleChoose();
        for (QuesMultipleChoose multipleChoose:MultipleChooselist){
            System.out.println(multipleChoose);

        }
        return new JsonResult(1012,codeMsg.getMultipleChoose());
    }
    @RequestMapping("/QuesJudge/findAll")
    @ResponseBody
    public JsonResult findAllQuesJudge(){
        List<QuesJudge> QuesJudgelist = questionManageService.findAllQuesJudge();
        for (QuesJudge quesJudge:QuesJudgelist){
            System.out.println(quesJudge);
        }
        return new JsonResult(1013,codeMsg.getJudge());
    }

    @RequestMapping("/QuestionsAnswers/findAll")
    @ResponseBody
    public JsonResult findAllQuesQuestionsAnswers(){
        List<QuesQuestionsAnswers> questionsAnswerslist = questionManageService.findAllQuesQuestionsAnswers();
        for (QuesQuestionsAnswers questionsAnswers:questionsAnswerslist){
            System.out.println(questionsAnswers);
        }
        return new JsonResult(1014,codeMsg.getQuestionAnswers());
    }
}
