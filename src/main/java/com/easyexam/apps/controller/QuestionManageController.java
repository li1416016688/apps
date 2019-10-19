package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.QuestionManageService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionManageController {
    @Autowired
    CodeMsg codeMsg;
    @Autowired
    QuestionManageService questionManageService;

    /**
     * 跳转页面  试题分类管理
     * @return
     */
    @RequiresPermissions({"test:type"})
    @RequestMapping("/singleChoose")
    public String singleChoose() {
        return "questUpdate";
    }

    @RequiresPermissions({"test:generate"})
    @RequestMapping("/createTestPaper")
    public String createTestPaper() {
        return "createTestPaper";
    }

    @RequiresPermissions({"test:type"})
    @RequestMapping("/createRandomTestPaper")
    public String createRandomTestPaper(){
        return "createRandomTestPaper";
    }

    @RequiresPermissions({"test:type"})
    @RequestMapping("/createPracticePaperDirect")
    public String createPracticePaperDirect(){
        return "createPracticePaperDirect";
    }


    /**
     * 展示所有的题目并进行分页
     */
    @RequestMapping("/singleChooseList.do")
    @ResponseBody
//    @RequiresPermissions({"test:type"})
    public Map listQuesChoose(Integer page, Integer limit, Integer subjectId, Integer quesId, String questionInfo) {


        HashMap<String, Object> map = new HashMap<>();

        if (quesId == null) {
            quesId = 1;
        }
        if (quesId == 2) {
            List<QuesMultipleChoose> list = questionManageService.finAllQuesMultipleChooses(subjectId, questionInfo, page, limit);
            map.put("count", ((Page) list).getTotal());
            map.put("data", list);
        } else if (quesId == 3) {
            List<QuesJudge> list = questionManageService.findAllQuesJudges(subjectId, questionInfo, page, limit);
            map.put("count", ((Page) list).getTotal());
            map.put("data", list);
        } else if (quesId == 4) {
            List<QuesQuestionsAnswers> list = questionManageService.findAllQuesQuestionsAnswers(subjectId, questionInfo, page, limit);
            map.put("count", ((Page) list).getTotal());
            map.put("data", list);
        } else {
            List<QuesSingleChoose> list = questionManageService.findAllQuesSingleChooses(subjectId, questionInfo, page, limit);
            map.put("count", ((Page) list).getTotal());
            map.put("data", list);
        }

        map.put("code", 0);
        return map;

    }

    /**
     * 导入excel
     *
     * @param file      必须为xlsx后缀文件
     * @param sheetName ="singleChoose"/"multipleChoose"/"judge"/"questionsAnswers"/"all" 不区分大小写
     */
    @PostMapping("/importExcel")
    @ResponseBody
    public JsonResult importQuestionFromExcel(@RequestParam("file") MultipartFile file, String sheetName) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename.contains(".xlsx")) {
            JsonResult jsonResult = questionManageService.importQuestionFromExcel(file, sheetName);
            return jsonResult;
        } else {
            //文件类型错误，直接返回
            return new JsonResult(ErrorCode.EXCEL_FILE_TYPE_ERROR, codeMsg.getExcelFileTypeError());
        }
    }

    /**
     * 添加一个单选题；其中选项不能为空A；其他约束参见config/questionConfig.properties
     */
    @PostMapping("/addQuesSingleChoose")
    @ResponseBody
    public JsonResult addQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        JsonResult jsonResult = questionManageService.addQuesSingleChoose(quesSingleChoose);
        return jsonResult;
    }

    /**
     * 添加一个多选题，其中答案必须包含&符号，其他约束参见config/questionConfig.properties
     */
    @PostMapping("/addQuesMultipleChoose")
    @ResponseBody
    public JsonResult addQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        JsonResult jsonResult = questionManageService.addQuesMultipleChoose(quesMultipleChoose);
        return jsonResult;

    }

    /**
     * 添加一个判断题，其中答案必须为boolean类型，其他约束参见config/questionConfig.properties
     */
    @PostMapping("/addQuesJudge")
    @ResponseBody
    public JsonResult addQuesJudge(QuesJudge quesJudge) {
        JsonResult jsonResult = questionManageService.addQuesJudge(quesJudge);
        return jsonResult;

    }

    /**
     * 添加简答题 添加一个问答题，约束参见config/questionConfig.properties
     */
    @PostMapping("/addQuesQuestionsAnswers")
    @ResponseBody
    public JsonResult addQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        JsonResult jsonResult = questionManageService.addQuesQuestionsAnswers(quesQuestionsAnswers);
        return jsonResult;

    }

    /**
     * 删除试题
     */
    @RequestMapping("/deleteQues.do")
    @ResponseBody
    public JsonResult deleteQuestById(Integer quesId, Integer id) {
        if (quesId == null || id == null || quesId < 1 || quesId > 4) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
        if (quesId == 2) {
            questionManageService.deleteMultipleChooseById(id);
        } else if (quesId == 3) {
            questionManageService.deleteQuesJudgeById(id);
        } else if (quesId == 4) {
            questionManageService.deleteQuestionsAnswerById(id);
        } else if (quesId == 1) {
            questionManageService.deleteSingleChooseById(id);
        }

        return new JsonResult(ErrorCode.DELETE_QUESTION_SUCCESS, codeMsg.getDeleteQuesSuccess());
    }

    /**
     * 修改单选题
     */
    @RequestMapping("/updateQuesSingleChoose.do")
    @ResponseBody
    public JsonResult updateQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        questionManageService.updateQuestionById(quesSingleChoose, 1);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesSuccess());
    }

    /**
     * 修改多选题
     */
    @RequestMapping("/updateQuesMultipleChoose.do")
    @ResponseBody
    public JsonResult updateQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        questionManageService.updateQuestionById(quesMultipleChoose, 2);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    /**
     * 修改判断题
     */
    @RequestMapping("/updateQuesJudge.do")
    @ResponseBody
    public JsonResult updateQuesJudge(QuesJudge quesJudge) {
        questionManageService.updateQuestionById(quesJudge, 3);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    /**
     * 修改简答题
     */
    @RequestMapping("/updateQuesQuestionsAnswers.do")
    @ResponseBody
    public JsonResult updateQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        questionManageService.updateQuestionById(quesQuestionsAnswers, 4);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    /**
     * 通过试题Id查询试题
     */
    @RequestMapping("findQues.do")
    @ResponseBody
    public JsonResult findQuestById(Integer quesId, Integer id) {
        Object quest = questionManageService.findQuestById(id, quesId);
        return new JsonResult(ErrorCode.FIND_QUESTION_SUCCESS, quest);
    }

    /**
     * 下载文件的方法，其中传入的fileName如果为quesTemplate，则下载试题导入的模板文件
     */
    @RequestMapping("/d")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream inputStream = null;

        try {
            if ("quesTemplate".equals(fileName)) {
                File file = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/static/tempExcel/quesTemplate.xlsx");
                inputStream = new FileInputStream(file);
                response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 手动生成试卷,将试题放入redis购物车中
     */
    @RequestMapping("/addQuestToRedis")
    @ResponseBody
    public JsonResult addQuestToRedis(PaperQuestion paperQuestion, Integer uid) {
        questionManageService.addQuestPaperRedis(paperQuestion, 4);
        return new JsonResult(ErrorCode.SUCCESS, codeMsg.getSuccess());
    }

    /**
     * 手动生成试卷,将试题redis购物车中移除
     */
    @RequestMapping("/deleteQuestToRedis")
    @ResponseBody
    public JsonResult deleteQuestToRedis(Integer id, Integer quesTypeId, Integer uid) {
        questionManageService.deleteQuestToRedis(id, quesTypeId, 4);
        return new JsonResult(ErrorCode.SUCCESS, codeMsg.getSuccess());
    }

    /**
     * 手动生成试卷,将试题redis购物车中试题展示到前台
     */
    @RequestMapping("/showPaperListOnRedis.do")
    @ResponseBody
    public Map showPaperListOnRedis(Integer questType, Integer page, Integer limit) {

        List<ShowQuestFromRedis> list =questionManageService.showPaperListOnRedis1(4, questType, page, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", ((Page) list).getTotal());
        map.put("data", list);
        map.put("code", 0);

        return map;
    }

    /**
     * 手动生成试卷,将试题redis购物车中试题提交到MySQL
     */
    @RequestMapping("/addQuesToMySql.do")
    @ResponseBody
    public JsonResult addQuesToMySql(Paper paper) {
        questionManageService.addQuestToMysql(paper, true);
        return new JsonResult(ErrorCode.SUCCESS, codeMsg.getSuccess());
    }

    @RequestMapping("createPaper")
    public String createPaper() {
        return "createTestPaper.html";
    }

}
