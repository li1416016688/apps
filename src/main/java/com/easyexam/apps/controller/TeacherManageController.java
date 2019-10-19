package com.easyexam.apps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeacherManageController {

    //跳转页面
    @RequestMapping("/teacher/list")
    public String teacherList() {
        return "list";
    }

    @RequestMapping("/noPerms")
    public String noPerms() {
        return "noPerms";
    }

}
