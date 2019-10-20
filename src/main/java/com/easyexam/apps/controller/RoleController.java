package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Role;
import com.easyexam.apps.entity.RoleTree;
import com.easyexam.apps.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/findAllRole")
    public String findAllRole1(){
        return "roleAdmin";
    }

    @RequestMapping("/findAllRole.do")     //查找所有角色
    @ResponseBody
    public Map<String, Object> findAllRole(){
        List<Role> list = roleService.findAllRole();
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",list);
        return map;
    }

    @RequestMapping("/addRole.do")  //添加角色
    @ResponseBody
    public JsonResult addRole(Role role){
        List<Integer> pid = role.getPid();
        return roleService.addRole(role,pid);
    }

    @RequestMapping("/deleteRole.do")  //删除角色   注意角色关联
    @ResponseBody
    public JsonResult deleteRole(Integer rid){
        return roleService.deleteRole(rid);
    }

    @RequestMapping("/updateRole.do")   //修改角色信息
    @ResponseBody
    public JsonResult updateRole(Role role){
        return roleService.updateRole(role);
    }

    @RequestMapping("/findRoleTree.do")  //获得角色树
    @ResponseBody
    public Map<String, Object> findRoleTree(){
        List<RoleTree> list = roleService.findRoleTree();
        Map<String, Object> map = new HashMap<>();
        map.put("data",list);
        return map;
    }


}
