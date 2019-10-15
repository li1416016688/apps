package com.easyexam.apps.controller;

import com.easyexam.common.CodeMsg;
import com.easyexam.common.JsonResult;
import com.easyexam.entity.QuesSingleChoose;
import com.easyexam.service.QuestionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class QuestionManageController {
    @Autowired
    private QuestionManageService questionManageService;
    @Autowired
    private CodeMsg codeMsg;
    @RequestMapping("/QuesSingleChoose/findAll")
    public JsonResult findAllQuesSingleChoose(){
        List<QuesSingleChoose> allQuesSingleChoose = questionManageService.findAllQuesSingleChoose();
        for (QuesSingleChoose singleChoose:allQuesSingleChoose){
            System.out.println(singleChoose);
        }
        return new JsonResult(1011,codeMsg.getSingleChoose());
    }
}
