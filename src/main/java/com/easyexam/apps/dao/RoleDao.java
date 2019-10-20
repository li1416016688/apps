package com.easyexam.apps.dao;

import com.easyexam.apps.entity.Role;
import com.easyexam.apps.entity.RoleTree;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
    public List<Role> findAllRole();//查询
    public int addRole(Role role);//添加
    public int addRolePermission(@Param("rid")Integer rid, @Param("pid")List<Integer> pid);
    public int deleteRole(Integer rid);// 删除角色
    public int deleteRoleUser(Integer rid);//删除角色对应的权限
    public int deleteUser(Integer rid);   //删除角色对应的用户
    public int updateRole(Role role);//修改角色信息

    public List<RoleTree> findRoleTree(); //获取角色树的结构


}
