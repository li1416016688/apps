package com.easyexam.apps.dao;

import com.easyexam.entity.QuesSingleChoose;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionManageDao {

    //查找所有的单选题
    public List<QuesSingleChoose> findAllQuesSingleChoose();
}
