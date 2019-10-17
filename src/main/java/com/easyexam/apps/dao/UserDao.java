package com.easyexam.apps.dao;

import com.easyexam.apps.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    public List<User> findAllUser();  //遍历所有用户
    public int deleteUser(Integer uid);  //删除用户
    public int deleteUserRole(Integer uid);  //删除用户对应角色(中间表）
    public User findUser(Integer uid);  //查找单个用户信息
    public int updateUser(User user);  //修改用户信息
}
