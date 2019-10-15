package com.easyexam.apps.service.impl;

import com.easyexam.apps.dao.UserDao;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<User> list = userDao.findAllUser();
        return list;
    }

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        List<User> list = service.findAll(1, 1);
        for (User l : list){
            System.out.println(l);
        }
    }
}
