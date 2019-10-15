package com.easyexam.apps.controller;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
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

    @RequestMapping("/singleChoose")
    public String singleChoose() {
        return "questUpdate";
    }

    @RequestMapping("/singleChooseList.do")
    @ResponseBody
    public Map listQuesChoose(Integer page, Integer limit, Integer subjectId, Integer quesId, String info) {


        HashMap<String, Object> map = new HashMap<>();

        if (quesId == null) {
            quesId = 1;
        }
        if (quesId == 2) {
            List<QuesMultipleChoose> list = questionManageService.finAllQuesMultipleChooses(subjectId, info, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else if (quesId == 3) {
            List<QuesJudge> list = questionManageService.findAllQuesJudges(subjectId, info, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else if (quesId == 4) {
            List<QuesQuestionsAnswers> list = questionManageService.findAllQuesQuestionsAnswers(subjectId, info, page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        } else {
            List<QuesSingleChoose> list = questionManageService.findAllQuesSingleChooses(subjectId, info,page, limit);
            map.put("count", ((Page)list).getTotal());
            map.put("data", list);
        }

        map.put("code", 0);
        return map;



    }


}
