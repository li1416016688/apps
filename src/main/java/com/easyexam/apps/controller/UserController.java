package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.service.UserService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("findAllUser")
    public String finAll1() {
        return "userAdmin";
    }

    @RequestMapping("/findAllUser.do")
    @ResponseBody
    public Map<String, Object> findAll1(Integer page, Integer limit, String name) {
        List<User> list = userService.findAll(page, limit, name);
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", ((Page) list).getTotal());
        map.put("data", list);
        return map;
    }

    @ResponseBody
    @RequestMapping("/deleteOneUser.do")
    public JsonResult deleteOneUser(Integer uid) {
        return userService.deleteOneUser(uid);
    }

    @ResponseBody
    @RequestMapping("/updateUser.do")
    public JsonResult updateUser(User user) {
        return userService.updateUser(user);
    }

    @ResponseBody
    @RequestMapping("/findUser.do")
    public JsonResult findUser(Integer uid) {
        User user = userService.findUser(uid);
        return new JsonResult(1, user);
    }

    @ResponseBody
    @RequestMapping("/findUserPermission.do")
    public JsonResult findUserPermission(Integer uid) {
        List<Integer> list = userService.findUserPermission(uid);
        return new JsonResult(1, list);
    }

    @ResponseBody
    @RequestMapping("/findUserRole.do")
    public JsonResult findUserRole(Integer uid) {
        List<Integer> list = userService.findUserRole(uid);
        return new JsonResult(1, list);
    }
}
