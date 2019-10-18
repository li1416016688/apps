package com.easyexam.apps.realm;

import com.easyexam.apps.entity.User;
import com.easyexam.apps.service.TeacherManageService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.HashSet;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Lazy //使用redis缓存shiro中数据时，需要使用注解
    @Autowired
    private TeacherManageService teacherManageService;

    //获取授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //获取合法登录的用户的用户名
        String name = (String) principalCollection.getPrimaryPrincipal();

        List<String> permsList = teacherManageService.findPermsByName(name);
        System.out.println(permsList);

        //创建授权信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(new HashSet<>(permsList));
        return info;
    }

    //获取认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取用户的身份信息
        String name = (String) authenticationToken.getPrincipal();
        //认证信息对象
        SimpleAuthenticationInfo info = null;
        //从数据库中获取用户信息
        User user = teacherManageService.findUserByName(name);
        System.out.println(user);
        if (user == null) {
            info = new SimpleAuthenticationInfo("", "", this.getName());
        } else {
            //第一个参数 用户身份信息
            //第二个参数 用户的合法密码
            //第三个参数 realm的名称
            info = new SimpleAuthenticationInfo(name, user.getPassword(), this.getName());
            //如果MD5使用盐值,需要在认证信息对象设值盐值
//            info = new SimpleAuthenticationInfo(name, user.getPassword(), ByteSource.Util.bytes("haha"), this.getName());
        }

        return info;
    }

  /*  public static void main(String[] args) {
        // 算法名 原值 盐值 迭代次数
        SimpleHash simpleHash = new SimpleHash("md5", "123", "haha", 1);

        System.out.println(simpleHash.toHex());
    }*/


}
