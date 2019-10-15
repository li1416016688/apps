package com.easyexam.service;

import com.easyexam.entity.Student;

public interface StudentService {
    public Student studentLogin(String idCard, String password);
}
