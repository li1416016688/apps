package com.easyexam.dao;

import com.easyexam.entity.QuesSingleChoose;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionManageDao {

    List<QuesSingleChoose> findAllQuesSingleChooses();

}
