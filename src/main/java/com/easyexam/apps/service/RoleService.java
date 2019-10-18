package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Role;

import java.util.List;

public interface RoleService {
    public List<Role> findAllRole();
    public JsonResult addRole(Role role,List<Integer>pid);
    public JsonResult deleteRole(Integer rid);
    public JsonResult updateRole(Role role);
}
