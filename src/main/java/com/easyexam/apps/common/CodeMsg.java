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

    @Value("${code.2341}")
    private String subjectIdNotFound;

    @Value("${code.2342}")
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
}
