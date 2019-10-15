package com.easyexam.apps.common;

public class JsonResult {

    private Integer code;
    private Object info;

    public JsonResult() {
    }

    public JsonResult(Integer code, Object info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
