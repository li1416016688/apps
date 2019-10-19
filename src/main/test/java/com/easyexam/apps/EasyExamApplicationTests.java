package com.easyexam.apps;

import com.easyexam.apps.dao.QuestionManageDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.apps.dao")
public class EasyExamApplicationTests {
    @Autowired
    QuestionManageDao questionManageDao;

    @Test
    public void contextLoads() {
        Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
        System.out.println("长度为："+allSubjectId.size());
    }



}
