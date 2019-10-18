package com.easyexam.apps.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Data
@PropertySource(value = {"classpath:config/questionConfig.properties"},
        ignoreResourceNotFound = false, encoding = "UTF-8", name = "questionConfig.properties")
public class QuestionConfig {
    @Value("${singleChoose.question}")
    private int SingleChooseQuestion;

    @Value("${singleChoose.choose}")
    private int SingleChooseChoose;

    @Value("${singleChoose.answer}")
    private int SingleChooseAnswer;

    @Value("${singleChoose.maxLevel}")
    private int SingleChooseMaxLevel;

    @Value("${singleChoose.tag}")
    private int SingleChooseTag;

    @Value("${multipleChoose.maxLevel}")
    private int MultipleChooseMaxLevel;

    @Value("${multipleChoose.question}")
    private int MultipleChooseQuestion;

    @Value("${multipleChoose.choose}")
    private int MultipleChooseChoose;

    @Value("${multipleChoose.answer}")
    private int MultipleChooseAnswer;

    @Value("${multipleChoose.tag}")
    private int MultipleChooseTag;

    @Value("${judge.maxLevel}")
    private int JudgeMaxLevel;

    @Value("${judge.question}")
    private int JudgeQuestion;

    @Value("${judge.tag}")
    private int JudgeTag;

    @Value("${quesAnswers.maxLevel}")
    private int QuesAnswersMaxLevel;

    @Value("${quesAnswers.question}")
    private int QuesAnswersQuestion;

    @Value("${quesAnswers.answer}")
    private int QuesAnswersAnswer;

    @Value("${quesAnswers.tag}")
    private int QuesAnswersTag;


}
