package com.easyexam.apps.dao;

import com.easyexam.apps.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao {    //对用户进行角色操作
    public int deleteUserRole(Integer uid);  //删除    （注册时已经添加）
    public int addUserRole(@Param("rids") List<Integer> rids, @Param("uid") Integer uid);//添加
    public List<UserRole> findById(Integer uid);   //根据用户id查自己的角色
}
