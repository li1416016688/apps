package com.easyexam.exection;

import com.easyexam.common.JsonResult;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionResolver {

    @ExceptionHandler(MyException.class)
    public JsonResult myException(MyException ex){
        return new JsonResult(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public JsonResult commonException(Exception ex){
        return new JsonResult(500, ex.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public String nopermException(AuthorizationException ex) {
        return "noperms";
    }

    @ExceptionHandler(RuntimeException.class)
    public JsonResult runtimeException(RuntimeException ex) {
        return new JsonResult(0, ex.getMessage());
    }
}
