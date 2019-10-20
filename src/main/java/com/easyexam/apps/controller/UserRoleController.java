package com.easyexam.apps.controller;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.UserRole;
import com.easyexam.apps.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/updateUserRole.do")
    public JsonResult updateUserRole(UserRole userRole){
        List<Integer> rids = userRole.getRid();
        Integer uid = userRole.getUid();
        JsonResult result = userRoleService.updateUserRole(rids, uid);
        return result;
    }

    @RequestMapping("/findByIdUserRole.do")
    public Map<String, Object> findById(Integer uid){
        List<UserRole> list = userRoleService.findById(uid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        return map;
    }

}
