package com.easyexam.apps.dao;

import com.easyexam.apps.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {
    public List<Permission> findAllPermission();//查找所有
    public int addPermission(Permission permission);//添加
    public int deletePermission(Integer pid);// 删除权限
    public int deletePermissionRole(Integer pid);//删除权限对应的角色
    public int updatePermission(Permission permission);//修改权限
}
