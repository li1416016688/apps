package com.easyexam.apps.dao;

import com.easyexam.apps.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherManageDao {

    /**
     * 登录
     */
    public User findUserByName(@Param(value = "username") String name);

    /**
     * 获取登录用户的权限信息的列表
     */
    public List<String> findPermsByName(String name);

}
