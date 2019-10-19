package com.easyexam.apps.filter;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.utils.MD5Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String method = req.getMethod();
        // 向请求头中写入数据后，会自动先发一个提交方式是OPTIONS的请求
        // 我们不用处理该请求，直接返回
        if (method.equalsIgnoreCase("OPTIONS")) {
            return;
        }

        String uri = req.getRequestURI();
        //如果uri包含 login 则为登录页面，直接放行
        if (uri.contains("login")) {
            chain.doFilter(request, response);
        } else {
            //从请求头中获取token
            String token = req.getHeader("token");
            //获取ajax核心，判断是否为ajax
            String ajax = req.getHeader("x-requested-with");
            //从redis中查找是存在token
            String md5 = MD5Utils.md5(token);
            //如果传入的token为空或redis中无此token，则返回json格式的权限不够
            if(token == null || token.equals("")) {
                //查找是否存在，返回一个String类型
                if ( ajax != null && ajax.equals("x-requested-with")) {
                    JsonResult result = new JsonResult(0, "权限不够");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonStr = objectMapper.writeValueAsString(result);
                    resp.getWriter().write(jsonStr);
                    return;
                } else if (token != null) {
                    String bo = stringRedisTemplate.opsForValue().get(token);
                    if (bo != null) {
                        chain.doFilter(request, response);
                    }
                }
            } else {
                System.out.println(req.getContextPath());
                resp.sendRedirect(req.getContextPath() + "/login.html");
            }

        }


    }

    @Override
    public void destroy() {

    }
}
