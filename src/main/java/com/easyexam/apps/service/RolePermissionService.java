package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.RolePermission;

import java.util.List;

public interface RolePermissionService {

    public JsonResult updateRolePermission(Integer rid, List<Integer> pid);
    public List<RolePermission> findById(Integer rid);
}
