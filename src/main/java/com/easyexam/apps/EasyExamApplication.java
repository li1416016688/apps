package com.easyexam.apps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.easyexam.apps.dao")
public class EasyExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExamApplication.class, args);
    }

}
