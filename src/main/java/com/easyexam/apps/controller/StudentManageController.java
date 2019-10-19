package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.service.StudentManageService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@ResponseBody
public class StudentManageController {
    @Autowired
    private StudentManageService studentManageService;


    @Autowired
    private CodeMsg codeMsg;

    //查找所有的考场信息
    @RequestMapping(value = "/Examinee/findAll" ,method = RequestMethod.GET)
    public Map findAllExaminee(Integer page, Integer limit){
        HashMap<String, Object> map = new HashMap<>();
        List<Student> ExamineeList = studentManageService.findAllExaminee(page, limit);
        map.put("count", ((Page)ExamineeList).getTotal());
        map.put("data", ExamineeList);
        map.put("code", 0);
//        System.out.println();
        return map;
    }



}
