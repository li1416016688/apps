package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionManageService {
    public JsonResult importQuestionFromExcel(MultipartFile file, String sheetType);

    public JsonResult addQuesSingleChoose(@RequestBody QuesSingleChoose quesSingleChoose);

    public JsonResult addQuesMultipleChoose(@RequestBody QuesMultipleChoose quesMultipleChoose);

    public JsonResult addQuesJudge(@RequestBody QuesJudge quesJudge);

    public JsonResult addQuesQuestionsAnswers(@RequestBody QuesQuestionsAnswers quesQuestionsAnswers);

    List<QuesSingleChoose> findAllQuesSingleChooses(Integer subjectId, String info, Integer page, Integer limit);

    List<QuesMultipleChoose> finAllQuesMultipleChooses(Integer subjectId, String info, Integer page, Integer limit);

    List<QuesJudge> findAllQuesJudges(Integer subjectId, String info, Integer page, Integer limit);

    List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(Integer subjectId, String info, Integer page, Integer limit);

    void deleteSingleChooseById(Integer id);

    void deleteMultipleChooseById(Integer id);

    void deleteQuesJudgeById(Integer id);

    void deleteQuestionsAnswerById(Integer id);

    void updateQuestionById(Object e, Integer quesId);

    Object findQuestById(Integer id, Integer quesId);

    void addQuestPaperRedis(PaperQuestion paperQuestion, Integer uid);

    List<PaperQuestion> addQuestToMysql(Paper paper, boolean bo );

    void deleteQuestToRedis(Integer id, Integer quesTypeId, Integer uid);

    List<ShowQuestFromRedis> showPaperListOnRedis1(Integer uid, Integer questType, Integer page, Integer limit);

}