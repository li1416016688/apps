package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.pojo.RandomPaper;
import com.easyexam.apps.service.PaperManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaperManageController {
    @Autowired
    CodeMsg codeMsg;
    @Autowired
    PaperManageService paperManageService;

    /**
     * 该接口为生成题目的接口，需要传入
     * level/subjectId/questionCount
     * 三种参数，返回值为提示代码和整张试卷信息，方便后续设定分数
     * 重要！！该方法传入的json格式如下：
     * {
     * 	"name":"xxx等级考试",
     * 	"level":3,
     * 	"subjectId":1,
     * 	"questionCount":[10,5,5,5],
     * }
     * @param randomPaper
     * @return
     */
    @GetMapping("/createRandomExamPaper.do")
    @ResponseBody
    public JsonResult createRandomPaper(@RequestBody RandomPaper randomPaper){
        //利用shiro获取发送该请求用户的id，这个id是makeId（创建者），这里为了方便测试，将其设定为1；
        //ToDo:删除这个测试用makeId
        int makeId = 1;
        JsonResult jsonResult = paperManageService.createRandomPaper(randomPaper,makeId);
        return jsonResult;
    }
}
