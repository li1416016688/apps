package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.TeacherManageDao;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.TeacherManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
//设置缓存的名称
@CacheConfig(cacheNames = "user")
@Service
public class TeacherManageServiceImpl implements TeacherManageService {

    @Autowired
    private TeacherManageDao teacherManageDao;

    @Override
    public User findUserByName(String name) {
        User user = teacherManageDao.findUserByName(name);
        if (user == null) {
            throw new MyException(0, "账号或密码错误");
        }
        return user;
    }

    //进行缓存，缓存方法的返回值 缓存key值
    @Cacheable(key = "'perms'.concat(#name)")
    @Override
    public List<String> findPermsByName(String name) {
        List<String> perms = teacherManageDao.findPermsByName(name);
        if (perms == null) {
            throw new MyException(0, "权限认证异常");
        }
        return perms;
    }
}
