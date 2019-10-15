package com.easyexam.apps.dao;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QuestionManageDao {

    //查找所有的单选题
    public List<QuesSingleChoose> findAllQuesSingleChoose();
    //查找所有的多选题
    public List<QuesMultipleChoose> findAllQuesMultipleChoose();
    //查找所有的判断题
    public List<QuesJudge> findAllQuesJudge();
    //查找所有的问答题
    public List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers();

}
