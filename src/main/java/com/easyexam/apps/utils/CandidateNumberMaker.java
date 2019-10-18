/**
 * 考号生成器
 * 考号规则：当前年份+当前年增加的学生数量
 */
package com.easyexam.apps.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CandidateNumberMaker {
    private int CandidateNumber;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public int getCandidateNumber(){
        Date date = new Date();
        int year = date.getYear() + 1900;
        CandidateNumber = year * 1000000;

        //从redis获取该年份的编号
        String number = (String) stringRedisTemplate.opsForHash().get("numberOfRegisteredStuOfYear", String.valueOf(year));
        if(number == null){
            stringRedisTemplate.opsForHash().put("numberOfRegisteredStuOfYear",String.valueOf(year),String.valueOf(1));
            return CandidateNumber + 1;
        }else{
            stringRedisTemplate.opsForHash().put("numberOfRegisteredStuOfYear",String.valueOf(year),String.valueOf(Integer.valueOf(number) + 1));
            return CandidateNumber + Integer.valueOf(number) + 1;
        }
    }
}
