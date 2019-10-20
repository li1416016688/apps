package com.easyexam.apps.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionDao {
    public int deleteRolePermission(Integer rid);  //删除该角色和所有权限
    public int addRolePermission(@Param("rid") Integer rid, @Param("pid")List<Integer> pid);//添加该角色和所有权限
    public List<Integer> findById(Integer rid);   //根据角色id查自己的权限
}
