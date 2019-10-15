package com.easyexam.controller;

import com.easyexam.entity.QuesSingleChoose;
import com.easyexam.service.QuestionManageService;
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
    public Map listQuesSingleChoose() {
        List<QuesSingleChoose> list = questionManageService.findAllQuesSingleChooses();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("data", list);
        return map;
    }


}
