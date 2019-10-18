package com.easyexam.apps.exection;

public class SheetNotFoundException extends Exception {
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SheetNotFoundException(){
        super();
    }

    public SheetNotFoundException(Integer code, String message){
        super();
        this.code = code;
        this.message = message;
    }
}
