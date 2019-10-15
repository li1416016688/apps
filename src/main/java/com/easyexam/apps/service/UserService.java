package com.easyexam.apps.service;

import com.easyexam.apps.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll(Integer page, Integer limit);
}
