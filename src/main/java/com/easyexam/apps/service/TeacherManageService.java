package com.easyexam.apps.service;

import com.easyexam.apps.entity.User;

import java.util.List;

public interface TeacherManageService {

    public User findUserByName(String name);

    public List<String> findPermsByName(String name);
}
