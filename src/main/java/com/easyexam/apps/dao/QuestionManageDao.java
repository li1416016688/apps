package com.easyexam.apps.dao;

import com.easyexam.apps.entity.*;

import java.util.List;
import java.util.Set;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionManageDao {
    //写入题目的方法（共四种题目类型）
    public int importQuesSingleChoose(List<QuesSingleChoose> quesSingleChoose);
    public int importQuesMultipleChoose(List<QuesMultipleChoose> quesMultipleChoose);
    public int importQuesJudge(List<QuesJudge> quesJudge);
    public int importQuesQuestionsAnswers(List<QuesQuestionsAnswers> quesQuestionsAnswers);

    //查询所有的subjectId
    public Set<Integer> findAllSubjectId();
    List<QuesSingleChoose> findAllQuesSingleChooses(@Param(value = "subjectId") Integer subjectId,
                                                    @Param(value = "info") String info);

    List<QuesMultipleChoose> finAllQuesMultipleChooses(@Param(value = "subjectId") Integer subjectId,
                                                       @Param(value = "info") String info);

    List<QuesJudge> findAllQuesJudges(@Param(value = "subjectId") Integer subjectId,
                                      @Param(value = "info") String info);

    List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(@Param(value = "subjectId") Integer subjectId,
                                                           @Param(value = "info") String info);

    int deleteSingleChooseById(Integer id);

    int deleteMultipleChooseById(Integer id);

    int deleteQuesJudgeById(Integer id);

    int deleteQuestionsAnswerById(Integer id);


    int updateSingleChooseById(QuesSingleChoose quesSingleChoose);

    int updateMultipleChooseById(QuesMultipleChoose quesMultipleChoose);

    int updateQuesJudgeById(QuesJudge quesJudge);

    int updateQuestionsAnswerById(QuesQuestionsAnswers quesQuestionsAnswers);

    QuesSingleChoose findQuesSingleChooseById(Integer id);

    QuesMultipleChoose findQuesMultipleChooseById(Integer id);

    QuesJudge findQuesJudgeById(Integer id);

    QuesQuestionsAnswers findQuestionsAnswerById(Integer id);

    QuestionType findQuestionType(Integer questionType);

}
