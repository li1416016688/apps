package com.easyexam.apps.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class IsLogined {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IsLogined isLogined;

    //验证登录的token
    public boolean getLoginStatus(String token){
        if(token == null){
            return false;
        }
        String id = stringRedisTemplate.opsForValue().get(token);
        if(id == null || "".equals(id)){
            //获取到的id值为空，没有登录
            return false;
        }else{
            String token2 = MD5Utils.md5(id);
            if(token.equals(token2)){
                //根据id生成的token相同，验证登录成功
                return true;
            }else{
                //根据id生成的token不同，验证登录失败
                return false;
            }
        }
    }

    //获取已登录登录用户的id，其中0为没有登录
    public int getUserId(String token){
        if(isLogined.getLoginStatus(token)){
            String id = stringRedisTemplate.opsForValue().get(token);
            return Integer.parseInt(id);
        }else{
            return 0;
        }

    }
}
