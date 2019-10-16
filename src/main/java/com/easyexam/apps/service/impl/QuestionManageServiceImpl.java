package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.common.QuestionConfig;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.SheetNotFoundException;
import com.easyexam.apps.exection.SubjectNotFoundException;
import com.easyexam.apps.service.QuestionManageService;
import com.easyexam.apps.utils.ReadExcel;
import com.sun.org.apache.bcel.internal.generic.TargetLostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {
    @Autowired
    CodeMsg codeMsg;
    @Autowired
    ReadExcel readExcel;
    @Autowired
    QuestionManageDao questionManageDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    QuestionConfig questionConfig;

    @Override
    public JsonResult importQuestionFromExcel(MultipartFile file, String sheetName) {
        Date date = new Date();
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/static/tempExcel";
        String fileName = date.getTime()+".xlsx";
        File tempFile = new File(path,fileName);
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SERVER_ERROR,codeMsg.getServerError());
        }

        //写入完成，读取进List
        List<QuesSingleChoose> quesSingleChooses = null;
        List<QuesMultipleChoose> quesMultipleChooses = null;
        List<QuesJudge> quesJudges = null;
        List<QuesQuestionsAnswers> quesQuestionsAnswers = null;
        try {
            if("singleChoose".equalsIgnoreCase(sheetName)){
                quesSingleChooses = readExcel.readSingleChoose(path +"/"+ fileName);
            }else if("multipleChoose".equalsIgnoreCase(sheetName)){
                quesMultipleChooses = readExcel.readMultipleChoose(path +"/"+ fileName);
            }else if("judge".equalsIgnoreCase(sheetName)){
                quesJudges = readExcel.readJudge(path + fileName);
            }else if("questionsAnswers".equalsIgnoreCase(sheetName)){
                quesQuestionsAnswers = readExcel.readQuestionsAnswers(path +"/"+ fileName);
            }else if("all".equalsIgnoreCase(sheetName)){
                quesSingleChooses = readExcel.readSingleChoose(path +"/"+ fileName);
                quesMultipleChooses = readExcel.readMultipleChoose(path +"/"+ fileName);
                quesJudges = readExcel.readJudge(path +"/"+ fileName);
                quesQuestionsAnswers = readExcel.readQuestionsAnswers(path +"/"+ fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SERVER_ERROR,codeMsg.getServerError());
        } catch (SheetNotFoundException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.EXCEL_SHEET_NOT_FOUND,codeMsg.getExcelSheetNotFound());
        } catch (SubjectNotFoundException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SUBJECT_ID_NOT_FOUND,codeMsg.getSubjectIdNotFound());
        } catch (NullPointerException e){
            e.printStackTrace();
            return new JsonResult(ErrorCode.EXCEL_CELL_IS_NULL,codeMsg.getExcelCellIsNull());
        }

        //删除文件
        tempFile.delete();

        //写入数据库
        int count = 0;      //计数器
        int totalCount = 0; //总数计数器
        if(quesSingleChooses != null){
            count = questionManageDao.importQuesSingleChoose(quesSingleChooses);
            totalCount = totalCount + count;
        }
        if(quesMultipleChooses != null){
            count = questionManageDao.importQuesMultipleChoose(quesMultipleChooses);
            totalCount = totalCount + count;
        }
        if(quesJudges != null){
            System.out.println(quesJudges);
            count = questionManageDao.importQuesJudge(quesJudges);
            totalCount = totalCount + count;
        }
        if(quesQuestionsAnswers != null){
            System.out.println(quesQuestionsAnswers);
            count = questionManageDao.importQuesQuestionsAnswers(quesQuestionsAnswers);
            totalCount = totalCount + count;
        }

        return new JsonResult(ErrorCode.EXCEL_IMPORT_SUCCESS,codeMsg.getExcelImportSuccess()+totalCount);
    }

    @Override
    public JsonResult addQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getSingleChooseAnswer();
        String answer = quesSingleChoose.getAnswer();
        if(answer.length() >answerLenth || !Character.isLetter(answer.charAt(0))){
            return new JsonResult(ErrorCode.ADD_QUES_ANSWER_WRONG,codeMsg.getAddQuesAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesSingleChoose.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for(Integer id : subjects){
            if(id == subjectId){
                redisContains = false;
                break;
            }
        }
        if(redisContains){
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for(Integer id:allSubjectId){
                redisTemplate.opsForSet().add("subject",id);
            }
            redisTemplate.expire("subject",900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if(!mysqlContains){
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG,codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesSingleChoose.getLevel();
        int maxLevel = questionConfig.getSingleChooseMaxLevel();
        if(level > maxLevel || level <= 0){
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG,codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/chooseA/chooseB/chooseC/chooseD/chosoeE/
         * chooseF/answer/tag
         */
        int chooseLenth = questionConfig.getSingleChooseChoose();
        int tagLenth = questionConfig.getSingleChooseTag();
        String tag = quesSingleChoose.getTag();
        if(tag != null){
            if(tag.length() > tagLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        //恐怖的6个选项的验证
        String chooseA = quesSingleChoose.getChooseA();
        if(chooseA == null){
            return new JsonResult(ErrorCode.AT_LEAST_ONE_CHOOSE,codeMsg.getAtLeastOneChoose());
        }else{
            if(chooseA.length() > chooseLenth)
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
        }
        String chooseB = quesSingleChoose.getChooseB();
        String chooseC = quesSingleChoose.getChooseC();
        String chooseD = quesSingleChoose.getChooseD();
        String chooseE = quesSingleChoose.getChooseE();
        String chooseF = quesSingleChoose.getChooseF();
        List<String> chooseList = Arrays.asList(chooseB,chooseC,chooseD,chooseE,chooseF);

        for(String choose : chooseList){
            if(choose != null){
                if(choose.length() > chooseLenth){
                    return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
                }
            }
        }

        //验证完成，写入数据库
        List<QuesSingleChoose> quesSingleChooses = new ArrayList<>();
        quesSingleChooses.add(quesSingleChoose);
        int i = questionManageDao.importQuesSingleChoose(quesSingleChooses);
        if(i==1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        }else{
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public JsonResult addQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getMultipleChooseAnswer();
        String answer = quesMultipleChoose.getAnswer();
        if(answer.length() >answerLenth || !answer.contains("&")){
            return new JsonResult(ErrorCode.ADD_QUES_MULTIPLE_ANSWER_WRONG,codeMsg.getAddQuesMultipleAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesMultipleChoose.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for(Integer id : subjects){
            if(id == subjectId){
                redisContains = false;
                break;
            }
        }
        if(redisContains){
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for(Integer id:allSubjectId){
                redisTemplate.opsForSet().add("subject",id);
            }
            redisTemplate.expire("subject",900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if(!mysqlContains){
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG,codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesMultipleChoose.getLevel();
        int maxLevel = questionConfig.getMultipleChooseMaxLevel();
        if(level > maxLevel || level <= 0){
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG,codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/chooseA/chooseB/chooseC/chooseD/chosoeE/
         * chooseF/answer/tag
         */
        int chooseLenth = questionConfig.getMultipleChooseChoose();
        int tagLenth = questionConfig.getMultipleChooseTag();
        String tag = quesMultipleChoose.getTag();
        if(tag != null){
            if(tag.length() > tagLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        //恐怖的6个选项的验证
        String chooseA = quesMultipleChoose.getChooseA();

        if(chooseA == null){
            return new JsonResult(ErrorCode.AT_LEAST_ONE_CHOOSE,codeMsg.getAtLeastOneChoose());
        }else{
            if(chooseA.length() > chooseLenth)
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
        }
        String chooseB = quesMultipleChoose.getChooseB();
        String chooseC = quesMultipleChoose.getChooseC();
        String chooseD = quesMultipleChoose.getChooseD();
        String chooseE = quesMultipleChoose.getChooseE();
        String chooseF = quesMultipleChoose.getChooseF();
        List<String> chooseList = Arrays.asList(chooseB,chooseC,chooseD,chooseE,chooseF);

        for(String choose : chooseList){
            if(choose != null){
                if(choose.length() > chooseLenth){
                    return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
                }
            }
        }

        //验证完成，写入数据库
        List<QuesMultipleChoose> quesMultipleChooses = new ArrayList<>();
        quesMultipleChooses.add(quesMultipleChoose);
        int i = questionManageDao.importQuesMultipleChoose(quesMultipleChooses);
        if(i==1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        }else{
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public JsonResult addQuesJudge(QuesJudge quesJudge) {
        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesJudge.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for(Integer id : subjects){
            if(id == subjectId){
                redisContains = false;
                break;
            }
        }
        if(redisContains){
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for(Integer id:allSubjectId){
                redisTemplate.opsForSet().add("subject",id);
            }
            redisTemplate.expire("subject",900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if(!mysqlContains){
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG,codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesJudge.getLevel();
        int maxLevel = questionConfig.getJudgeMaxLevel();
        if(level > maxLevel || level <= 0){
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG,codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/tag
         */
        int questionLenth = questionConfig.getJudgeQuestion();
        int tagLenth = questionConfig.getJudgeTag();
        String question = quesJudge.getQuestion();
        if(question != null){
            if(question.length() > questionLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        String tag = quesJudge.getTag();
        if(tag != null){
            if(tag.length() > tagLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }

        //验证完成，写入数据库
        List<QuesJudge> quesJudges = new ArrayList<>();
        quesJudges.add(quesJudge);
        int i = questionManageDao.importQuesJudge(quesJudges);
        if(i==1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        }else{
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public JsonResult addQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getQuesAnswersAnswer();
        String answer = quesQuestionsAnswers.getAnswer();
        if(answer.length() >answerLenth || !Character.isLetter(answer.charAt(0))){
            return new JsonResult(ErrorCode.ADD_QUES_QUESANSWERS_ANSWER_WRONG,codeMsg.getAddQuesQuesAnswersAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesQuestionsAnswers.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for(Integer id : subjects){
            if(id == subjectId){
                redisContains = false;
                break;
            }
        }
        if(redisContains){
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for(Integer id:allSubjectId){
                redisTemplate.opsForSet().add("subject",id);
            }
            redisTemplate.expire("subject",900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if(!mysqlContains){
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG,codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesQuestionsAnswers.getLevel();
        int maxLevel = questionConfig.getQuesAnswersMaxLevel();
        if(level > maxLevel || level <= 0){
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG,codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/tag
         */
        int questionLenth = questionConfig.getQuesAnswersQuestion();
        int tagLenth = questionConfig.getQuesAnswersTag();
        String question = quesQuestionsAnswers.getQuestion();
        if(question != null){
            if(question.length() > questionLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        String tag = quesQuestionsAnswers.getTag();
        if(tag != null){
            if(tag.length() > tagLenth){
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }

        //验证完成，写入数据库
        List<QuesQuestionsAnswers> quesQuestionsAnswersList = new ArrayList<>();
        quesQuestionsAnswersList.add(quesQuestionsAnswers);
        int i = questionManageDao.importQuesQuestionsAnswers(quesQuestionsAnswersList);
        if(i==1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        }else{
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }
}
