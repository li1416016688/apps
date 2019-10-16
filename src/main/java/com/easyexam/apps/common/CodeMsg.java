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

    @Value("${code.1015}")
    private String findExaminationroom;

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

    @Value("${code.2344}")
    private String ExcelSheetNotFound;

    @Value("${code.2345}")
    private String ExcelFileTypeError;
}
