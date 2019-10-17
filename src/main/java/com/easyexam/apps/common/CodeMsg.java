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

    @Value("${code.2341}")
    private String subjectIdNotFound;

    @Value("${code.2342}")
    private String ExcelCellIsNull;

    @Value("${code.3211}")
    private String DeleteException;

    @Value("${code.2231}")
    private String NoPointException;

    @Value("${code.3212}")
    private String InsertException;

    @Value("${code.3213}")
    private String UpdateException;
}
