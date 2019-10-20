package com.easyexam.apps.controller;

import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//根据学科统计平均分和最高分成绩
@Controller
public class StudentScoreController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/findSubjectScore")
    public String findScore(){
        return "subjectScores";
    }

    @RequestMapping(value = "/findSubjectScores")
    @ResponseBody
    public JsonResult findSubjectScore() {
        Map<String, Object> map = studentService.findSubjectScore();
        return new JsonResult(ErrorCode.SUCCESS, map);
    }
}
