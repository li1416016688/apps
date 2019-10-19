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

    @Value("${code.3211}")
    private String DeleteException;

    @Value("${code.2231}")
    private String NoPointException;

    @Value("${code.3212}")
    private String InsertException;

    @Value("${code.3213}")
    private String UpdateException;

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

    @Value("${code.2350}")
    private String CreatePaperSuccess;

    @Value("${code.2351}")
    private String CreatePaperParamLost;

    @Value("${code.2352}")
    private String DeletePaperSuccess ;

    @Value("${code.1103}")
    private String FindUserError;

    @Value("${code.2401}")
    private String AddQuesRedis;

    @Value("${code.2501}")
    private String idCardError;

    @Value("${code.1001}")
    private String success;
    @Value("${code.2502}")
    private String passwordError;
    @Value("${code.3501}")
    private String studentRegisterFail;
    @Value("${code.2503}")
    private String topicNotEnough;
    @Value("${code.2101}")
    private String showQuesListOnRedis;

    @Value("${code.2102}")
    private String AutoCreatePaper;



    @Value("${code.2400}")
    private String studentFindoneError;
    @Value("${code.4001}")
    private String studentNotFindone;
    @Value("${code.1019}")
    private String studentFindoneSuccess;
    @Value("${code.1005}")
    private String studentUpdateSuccess;
    @Value("${code.1006}")
    private String studentaddSuccess;

    @Value("${code.2401}")
    private String studentphoneError;
    @Value("${code.2402}")
    private String studentidCardError;
    @Value("${code.2403}")
    private String studentIdCardFormatError;
    @Value("${code.1419}")
    private String examinationRoomAddSuccess;

}
