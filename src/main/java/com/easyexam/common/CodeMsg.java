package com.easyexam.common;

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
}
