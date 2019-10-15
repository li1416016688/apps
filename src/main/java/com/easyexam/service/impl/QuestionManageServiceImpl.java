package com.easyexam.service.impl;

import com.easyexam.dao.QuestionManageDao;
import com.easyexam.entity.QuesSingleChoose;
import com.easyexam.service.QuestionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {

    @Autowired
    private QuestionManageDao questionManageDao;

    public List<QuesSingleChoose> findAllQuesSingleChooses() {
        return questionManageDao.findAllQuesSingleChooses();
    }
}
