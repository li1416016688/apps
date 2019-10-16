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

    //添加题目成功
    public static int ADD_QUES_SUCCESS=2330;

    //添加单选题时答案不为1位字母
    public static int ADD_QUES_ANSWER_WRONG=2331;

    //选择题至少需要一个选项
    public static int AT_LEAST_ONE_CHOOSE=2332;

    //多选题格式错误（不含&）
    public static int ADD_QUES_MULTIPLE_ANSWER_WRONG=2333;

    //问答题答案限制30000字，请重新录入
    public static int ADD_QUES_QUESANSWERS_ANSWER_WRONG=2334;

    //数据内容太长，请重新输录入
    public static int DATA_TOO_LONG=2337;

    //没有找到对应学科，请重新录入
    public static int ADD_QUES_SUBJECT_WRONG=2338;

    //难度等级为1~5，请重新录入
    public static int ADD_QUES_LEVEL_WRONG=2339;

    //Excel导入成功
    public static int EXCEL_IMPORT_SUCCESS = 2340;

    //Excel导入发生错误，学科不存在或模板有误
    public static int SUBJECT_ID_NOT_FOUND = 2341;

    //Excel导入发生错误，文件不存在
    public static int EXCEL_NOT_FOUND=2342;

    //Excel导入发生错误，题目关键信息为空
    public static int EXCEL_CELL_IS_NULL=2343;

    //Excel导入发生错误，模板信息有误（sheet不存在）
    public static int EXCEL_SHEET_NOT_FOUND=2344;

    //Excel格式异常，请上传xlsx格式的文件模板
    public static int EXCEL_FILE_TYPE_ERROR=2345;

}
