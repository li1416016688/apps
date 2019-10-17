package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.QuestionManageService;
import com.github.pagehelper.Page;
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

    @RequestMapping("/singleChoose")
    public String singleChoose() {
        return "questUpdate";
    }


    /**
     * 导入excel
     * @param file 必须为xlsx后缀文件
     * @param sheetName ="singleChoose"/"multipleChoose"/"judge"/"questionsAnswers"/"all" 不区分大小写
     * @return
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
     * @param quesSingleChoose
     * @return
     */
    @PostMapping("/addQuesSingleChoose")
    @ResponseBody
    public JsonResult addQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        JsonResult jsonResult = questionManageService.addQuesSingleChoose(quesSingleChoose);
        return jsonResult;
    }

    /**
     * 添加一个多选题，其中答案必须包含&符号，其他约束参见config/questionConfig.properties
     * @param quesMultipleChoose
     * @return
     */
    @PostMapping("/addQuesMultipleChoose")
    @ResponseBody
    public JsonResult addQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        JsonResult jsonResult = questionManageService.addQuesMultipleChoose(quesMultipleChoose);
        return jsonResult;

    }

    /**
     * 添加一个判断题，其中答案必须为boolean类型，其他约束参见config/questionConfig.properties
     * @param quesJudge
     * @return
     */
    @PostMapping("/addQuesJudge")
    @ResponseBody
    public JsonResult addQuesJudge(QuesJudge quesJudge) {
        JsonResult jsonResult = questionManageService.addQuesJudge(quesJudge);
        return jsonResult;

    }

    /**
     * 添加一个问答题，约束参见config/questionConfig.properties
     * @param quesQuestionsAnswers
     * @return
     */
    @PostMapping("/addQuesQuestionsAnswers")
    @ResponseBody
    public JsonResult addQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        JsonResult jsonResult = questionManageService.addQuesQuestionsAnswers(quesQuestionsAnswers);
        return jsonResult;

    }


    @RequestMapping("/singleChooseList.do")
    @ResponseBody
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

    @RequestMapping("/updateQuesSingleChoose.do")
    @ResponseBody
    public JsonResult updateQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        questionManageService.updateQuestionById(quesSingleChoose, 1);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesSuccess());
    }

    @RequestMapping("/updateQuesMultipleChoose.do")
    @ResponseBody
    public JsonResult updateQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        questionManageService.updateQuestionById(quesMultipleChoose, 2);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("/updateQuesJudge.do")
    @ResponseBody
    public JsonResult updateQuesJudge(QuesJudge quesJudge) {
        questionManageService.updateQuestionById(quesJudge, 3);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("/updateQuesQuestionsAnswers.do")
    @ResponseBody
    public JsonResult updateQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        questionManageService.updateQuestionById(quesQuestionsAnswers, 4);
        return new JsonResult(ErrorCode.UPDATE_QUESTION_SUCCESS, codeMsg.getUpdateQuesFail());
    }

    @RequestMapping("findQues.do")
    @ResponseBody
    public JsonResult findQuestById(Integer quesId, Integer id) {
        Object quest = questionManageService.findQuestById(id, quesId);
        return new JsonResult(ErrorCode.FIND_QUESTION_SUCCESS, quest);
    }

    /**
     * 下载文件的方法，其中传入的fileName如果为quesTemplate，则下载试题导入的模板文件
     * @param request
     * @param response
     * @param fileName
     */
    @RequestMapping("/d")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,String fileName){
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream inputStream = null;

        try {
            if ("quesTemplate".equals(fileName)) {
                File file = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/static/tempExcel/quesTemplate.xlsx");
                inputStream = new FileInputStream(file);
                response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
                IOUtils.copy(inputStream,response.getOutputStream());
                response.flushBuffer();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/addQuestToRedis")
    @ResponseBody
    public JsonResult addQuestToRedis(PaperQuestion paperQuestion, Integer uid) {
        questionManageService.addQuestPaperRedis(paperQuestion, uid);
        return new JsonResult(1, "添加成功");
    }

    @RequestMapping("/deleteQuestToRedis")
    @ResponseBody
    public JsonResult deleteQuestToRedis(PaperQuestion paperQuestion, Integer uid) {
        questionManageService.deleteQuestToRedis(paperQuestion, uid);
        return new JsonResult(1, "移除成功");
    }


    @RequestMapping("/showPaperListOnRedis")
    @ResponseBody
    public JsonResult showPaperListOnRedis(Integer uid) {
        Map<String, Object> map = questionManageService.showPaperListOnRedis(uid);
        return new JsonResult(1, map);
    }

    @RequestMapping("/addQuesToMySql")
    @ResponseBody
    public JsonResult addQuesToMySql(Paper paper) {
        questionManageService.addQuestToMysql(paper, true);
        return new JsonResult(1, "生成成功");
    }

}
