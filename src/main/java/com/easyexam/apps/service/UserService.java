package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll(Integer page, Integer limit);
    public JsonResult deleteOneUser(Integer uid);
    public JsonResult updateUser(User user);
    public User findUser(Integer uid);
    public List<Integer> findUserPermission(Integer uid);
    public List<Integer> findUserRole(Integer uid);
}
