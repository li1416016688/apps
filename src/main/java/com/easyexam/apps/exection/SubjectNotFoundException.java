package com.easyexam.apps.exection;

public class SubjectNotFoundException extends Exception {
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

    public SubjectNotFoundException(){
        super();
    }

    public SubjectNotFoundException(Integer code, String message){
        super();
        this.code = code;
        this.message = message;
    }

}
