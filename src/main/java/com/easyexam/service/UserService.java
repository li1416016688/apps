package com.easyexam.service;

import com.easyexam.entity.User;

import java.util.List;

public interface UserService {
    public List<User> findAll(Integer page, Integer limit);
}
