package com.easyexam.apps;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableTransactionManagement //开启事务支持
@MapperScan("com.easyexam.apps.dao")
@EnableCaching //启用缓存
public class EasyExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExamApplication.class, args);
    }

}
