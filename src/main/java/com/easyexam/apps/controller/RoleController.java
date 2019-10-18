package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Role;
import com.easyexam.apps.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAllRole.do")     //查找所有角色
    public Map<String, Object> findAllRole(){
        List<Role> list = roleService.findAllRole();
        Map<String, Object> map = new HashMap<>();
        map.put("data",list);
        return map;
    }

    @RequestMapping("/addRole.do")
    public JsonResult addRole(Role role){
        List<Integer> pid = role.getPid();
        return roleService.addRole(role,pid);
    }

    @RequestMapping("/deleteRole.do")
    public JsonResult deleteRole(Integer rid){
        return roleService.deleteRole(rid);
    }

    @RequestMapping("/updateRole.do")
    public JsonResult updateRole(Role role){
        return roleService.updateRole(role);
    }
}
