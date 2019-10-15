package com.easyexam.apps.common;
public class ErrorCode {
    private int code;
    private String msg;

    // success
    public static int SUCCESS = 0;
    // 服务器异常
    public static int SERVER_ERROR = 500;

    // 登录密码不能为空
    public static int PASSWORD_EMPTY = 1001;
    // 手机号不能为空
    public static int PHONE_EMPTY = 1002;
    // 手机号不存在
    public static int PHONE_ERROR = 1003;
    // 密码错误
    public static int PASSWORD_ERROR = 1004;
    // 用户未登录
    public static int USER_NO_LOGIN = 1005;

    //Excel导入发生错误，学科不存在或模板有误
    public static int SUBJECT_ID_NOT_FOUND = 2341;

    //Excel导入发生错误，文件不存在
    public static int EXCEL_NOT_FOUND=2342;

    //Excel导入发生错误，题目关键信息为空
    public static int EXCEL_CELL_IS_NULL=2343;

    //Excel导入发生错误，模板信息有误（sheet不存在）
    public static int EXCEL_SHEET_NOT_FOUND=2344;

}
