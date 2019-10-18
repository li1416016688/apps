package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.RolePermission;
import com.easyexam.apps.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @RequestMapping("/updateRolePermission.do")
    public JsonResult updateRolePermission(RolePermission rolePermission){
        List<Integer> pid = rolePermission.getPid();
        Integer rid = rolePermission.getRid();
        return rolePermissionService.updateRolePermission(rid, pid);
    }

    @RequestMapping("/findByIdRolePermission.do")
    public Map<String, Object> findById(Integer rid){
        List<RolePermission> list = rolePermissionService.findById(rid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return map;
    }
}
