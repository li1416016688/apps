package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.RolePermission;
import com.easyexam.apps.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping("/updateRolePermission.do")    //修改角色权限
    public JsonResult updateRolePermission(RolePermission rolePermission){
        List<Integer> pid = rolePermission.getPid();
        Integer rid = rolePermission.getRid();
        return rolePermissionService.updateRolePermission(rid, pid);
    }

    @RequestMapping("/findByIdRolePermission.do")   //树形角色页面，角色对应的权限id
    public JsonResult findById(Integer rid){
        List<Integer> list = rolePermissionService.findById(rid);
        return new JsonResult(1,list);
    }
}
