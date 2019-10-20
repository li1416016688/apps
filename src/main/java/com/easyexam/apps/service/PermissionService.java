package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Permission;
import com.easyexam.apps.entity.PermissionTree;

import java.util.List;

public interface PermissionService {
    public List<Permission> findAll(Integer page, Integer limit);
    public JsonResult addPermission(Permission permission);//添加
    public JsonResult deleteOnePermission(Integer pid);
    public JsonResult updatePermission(Permission permission);
    public List<PermissionTree> findZtr();
}
