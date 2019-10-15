package com.easyexam.controller;

import com.easyexam.common.JsonResult;
import com.easyexam.entity.User;
import com.easyexam.service.UserService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAllUser.do")
    public JsonResult findAll(Integer page, Integer limit){
        List<User> list = userService.findAll(page, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",((Page)list).getTotal());
        map.put("data",list);

        return new JsonResult(0,map);
    }
    @RequestMapping("/test")
    public JsonResult test(){
        return new JsonResult(0,"test");
    }

}
