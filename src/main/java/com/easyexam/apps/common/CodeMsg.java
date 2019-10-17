package com.easyexam.apps.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource(value = {"classpath:codemsg/codemsg.properties"},
        ignoreResourceNotFound = false, encoding = "UTF-8", name = "codemsg.properties")
public class CodeMsg {
    @Value("${code.0000}")
    private String example;
    @Value("${code.1011}")
    private String singleChoose;
    @Value("${code.1012}")
    private String multipleChoose;
    @Value("${code.1013}")
    private String judge;
    @Value("${code.1014}")
    private String questionAnswers;

    @Value("${code.2330}")
    private String AddQuesSuccess;

    @Value("${code.2331}")
    private String AddQuesAnswerWrong;

    @Value("${code.2332}")
    private String AtLeastOneChoose;

    @Value("${code.2333}")
    private String AddQuesMultipleAnswerWrong;

    @Value("${code.2334}")
    private String AddQuesQuesAnswersAnswerWrong;

    @Value("${code.2337}")
    private String DataTooLong;

    @Value("${code.2338}")
    private String AddQuesSubjectWrong;

    @Value("${code.2339}")
    private String AddQuesLevelWrong;

    @Value("${code.1015}")
    private String findAllExaminationroom;
    @Value("${code.1016}")
    private String findoneExaminationroom;
    @Value("${code.1017}")
    private String updateExaminationroom;
    @Value("${code.1018}")
    private String deleteExaminationroom;

    @Value("${code.2340}")
    private String ExcelImportSuccess;

    @Value("${code.500}")
    private String ServerError;

    @Value("${code.2341}")
    private String subjectIdNotFound;

    @Value("${code.2342}")
    private String ExcelNotFound;

    @Value("${code.2343}")
    private String ExcelCellIsNull;

    @Value("${code.2001}")
    private String deleteQuesFail;

    @Value("${code.1100}")
    private String deleteQuesSuccess;

    @Value("${code.2002}")
    private String updateQuesFail;

    @Value("${code.1101}")
    private String updateQuesSuccess;

    @Value("${code.2003}")
    private String findQuesFail;

    @Value("${code.1102}")
    private String findQuesSuccess;

    @Value("${code.2344}")
    private String ExcelSheetNotFound;

    @Value("${code.2345}")
    private String ExcelFileTypeError;
}
