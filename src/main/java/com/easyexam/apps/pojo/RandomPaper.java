package com.easyexam.apps.pojo;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import lombok.Data;

import java.util.List;

@Data
public class RandomPaper {
    private Integer level;  //整张试卷的难度
    private Integer singleChooseCount;  //单选题的数量
    private Integer multipleChooseCount;
    private Integer judgeCount;
    private Integer questionsAnswers;
    private List<QuesSingleChoose> quesSingleChooses;
    private List<QuesMultipleChoose> multipleChooses;
    private List<QuesJudge> judges;
    private List<QuesQuestionsAnswers> quesQuestionsAnswers;
}
