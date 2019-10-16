package com.easyexam.apps.common;

public class ErrorCode {
    private int code;
    private String msg;

    // success
    public static int SUCCESS = 1001;

    // 用户未登录
    public static int USER_NO_LOGIN = 1005;

    public static int ID_CARD_ERROR=2501;//身份证错误

    public static int PASSWORD_ERROR = 2502; // 密码错误

    public static int DELETE_QUESTION_FAIL = 4001; // 删除试题失败

    public static int DELETE_QUESTION_SUCCESS = 1100; //删除试题成功

    public static int UPDATE_QUESTION_FAIL = 4002;

    public static int UPDATE_QUESTION_SUCCESS = 1101; //修改试题成功

    public static int FIND_QUESTION_FAIL = 2003; //查找试题失败

    public static int FIND_QUESTION_SUCCESS = 1102;
}
