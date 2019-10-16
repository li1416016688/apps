package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

public interface QuestionManageService {
    public JsonResult importQuestionFromExcel(MultipartFile file, String sheetType);

    public JsonResult addQuesSingleChoose(@RequestBody QuesSingleChoose quesSingleChoose);
    public JsonResult addQuesMultipleChoose(@RequestBody QuesMultipleChoose quesMultipleChoose);
    public JsonResult addQuesJudge(@RequestBody QuesJudge quesJudge);
    public JsonResult addQuesQuestionsAnswers(@RequestBody QuesQuestionsAnswers quesQuestionsAnswers);
}
