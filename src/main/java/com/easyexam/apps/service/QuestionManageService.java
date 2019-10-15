package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionManageService {
    public JsonResult importQuestionFromExcel(MultipartFile file, String sheetType);
}
