package com.easyexam.dao;

import com.easyexam.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    public List<User> findAllUser();
}
