package com.easyexam.service;

import com.easyexam.entity.QuesSingleChoose;

import java.util.List;

public interface QuestionManageService {
    //    //查找所有的单选题
    public List<QuesSingleChoose> findAllQuesSingleChoose();
}
