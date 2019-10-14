package com.easyexam.exection;

import com.easyexam.common.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*@ControllerAdvice
@ResponseBody*/
@RestControllerAdvice
public class GlobalExceptionResolver {
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e) {
        return new JsonResult(0, e.getMessage());
    }

}
