package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.service.QuestionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class QuestionManageController {
    @Autowired
    CodeMsg codeMsg;
    @Autowired
    QuestionManageService questionManageService;

    /**
     *
     * @param file 必须为xlsx后缀文件
     * @param sheetName ="singleChoose"/"multipleChoose"/"judge"/"questionsAnswers"/"all" 不区分大小写
     * @return
     */
    @PostMapping("/importExcel")
    @ResponseBody
    public JsonResult importQuestionFromExcel(@RequestParam("file") MultipartFile file, String sheetName){
        String originalFilename = file.getOriginalFilename();
        if(originalFilename.contains(".xlsx")){
            JsonResult jsonResult = questionManageService.importQuestionFromExcel(file, sheetName);
            return jsonResult;
        }else{
            //文件类型错误，直接返回
            return new JsonResult(ErrorCode.EXCEL_FILE_TYPE_ERROR,codeMsg.getExcelFileTypeError());
        }
    }
}
