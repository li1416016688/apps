package com.easyexam.apps.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeacherLoginController {

    //跳转到登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "admin_login";
    }


    @PostMapping("/login")
    public String login(String name, String password) {
        //存储输入的用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        System.out.println("################" + token);
        //主体对象
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "admin_login";
        }

        Session session = subject.getSession();
        System.out.println(session  );
        return "index";
    }

}
