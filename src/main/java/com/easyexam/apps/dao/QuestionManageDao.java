package com.easyexam.apps.dao;

import com.easyexam.apps.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

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
                                                    @Param(value = "info") String info,
                                                    @Param(value = "ids") List<Integer> ids);

    List<QuesMultipleChoose> finAllQuesMultipleChooses(@Param(value = "subjectId") Integer subjectId,
                                                       @Param(value = "info") String info,
                                                       @Param(value = "ids") List<Integer> ids);

    List<QuesJudge> findAllQuesJudges(@Param(value = "subjectId") Integer subjectId,
                                      @Param(value = "info") String info,
                                      @Param(value = "ids") List<Integer> ids);

    List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(@Param(value = "subjectId") Integer subjectId,
                                                           @Param(value = "info") String info,
                                                           @Param(value = "ids") List<Integer> ids);

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

    User findUserById(Integer uid);

    int addPaperToMySql(@Param(value = "list") List<PaperQuestion> list);

    QuestionType findQuestionType(Integer questionType);

    List<QuestionType> findQuestionTypes();

    Subject findSubjectName(String subjectName);

    List<Subject> findSubject();

    int autoCreatePaper(Paper paper);
}
