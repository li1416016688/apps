package com.easyexam.apps.dao;

import com.easyexam.apps.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    public List<User> findAllUser();
}
