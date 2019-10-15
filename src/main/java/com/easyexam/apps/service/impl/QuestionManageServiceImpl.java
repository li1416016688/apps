package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import com.easyexam.apps.exection.SheetNotFoundException;
import com.easyexam.apps.exection.SubjectNotFoundException;
import com.easyexam.apps.service.QuestionManageService;
import com.easyexam.apps.utils.ReadExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {
    @Autowired
    CodeMsg codeMsg;
    @Autowired
    ReadExcel readExcel;
    @Autowired
    QuestionManageDao questionManageDao;

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
}
