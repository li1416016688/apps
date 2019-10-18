package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    public JsonResult updateUserRole(List<Integer> rids,Integer uid);
    public List<UserRole> findById(Integer uid);

}
