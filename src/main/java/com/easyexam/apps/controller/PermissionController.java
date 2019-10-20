package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Permission;
import com.easyexam.apps.entity.PermissionTree;
import com.easyexam.apps.service.PermissionService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController      //操作权限Permission
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/findAllPermission.do")
    public Map<String,Object> findAllPermission(Integer page, Integer limit){
        List<Permission> list = permissionService.findAll(page, limit);
        Map<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("count",((Page)list).getTotal());
        map.put("data",list);
        return map;
    }

    @RequestMapping("/addPermission.do")
    public JsonResult addPermission(Permission permission){
        return permissionService.addPermission(permission);
    }

    @RequestMapping("/deletePermission.do")
    public JsonResult deletePermission(Integer pid){
        return permissionService.deleteOnePermission(pid);
    }

    @RequestMapping("/updatePermission.do")
    public JsonResult updatePermission(Permission permission){
        return permissionService.updatePermission(permission);
    }

    @RequestMapping("/findZtr.do")
    public JsonResult findZtr(){
        List<PermissionTree> list = permissionService.findZtr();
        return new JsonResult(1,list);
    }


}
