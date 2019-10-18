package com.easyexam.apps.common;

import org.springframework.stereotype.Component;

@Component
public class ErrorCode {
    private int code;
    private String msg;

    //服务器错误
    public static int SERVER_ERROR = 500;

    // success
    public static int SUCCESS = 1001;

    // 用户未登录
    public static int USER_NO_LOGIN = 1005;

    public static int ID_CARD_ERROR=2501;//身份证错误

    public static int PASSWORD_ERROR = 2502; // 密码错误

    public static int STUDENT_REGISTER_FAIL=3501;//注册失败
    public static int TOPIC_NOT_ENOUGH=2503;//题库试题不够，或输入试题数不合法

    public static int DELETE_QUESTION_FAIL = 4001; // 删除试题失败

    public static int DELETE_QUESTION_SUCCESS = 1100; //删除试题成功

    public static int UPDATE_QUESTION_FAIL = 4002;

    public static int UPDATE_QUESTION_SUCCESS = 1101; //修改试题成功

    public static int FIND_QUESTION_FAIL = 2003; //查找试题失败

    public static int FIND_QUESTION_SUCCESS = 1102;
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

    //试卷生成成功
    public static int CREATE_PAPER_SUCCESS=2350;

    //随机生成失败，缺少必要的参数
    public static int CREATE_PAPER_PARAM_LOST=2351;



}
