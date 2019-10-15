package com.easyexam.apps.dao;

import com.easyexam.apps.entity.QuesJudge;
import com.easyexam.apps.entity.QuesMultipleChoose;
import com.easyexam.apps.entity.QuesQuestionsAnswers;
import com.easyexam.apps.entity.QuesSingleChoose;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionManageDao {

    List<QuesSingleChoose> findAllQuesSingleChooses(@Param(value = "subjectId") Integer subjectId,
                                                    @Param(value = "info") String info);

    List<QuesMultipleChoose> finAllQuesMultipleChooses(@Param(value = "subjectId") Integer subjectId,
                                                       @Param(value = "info") String info);

    List<QuesJudge> findAllQuesJudges(@Param(value = "subjectId") Integer subjectId,
                                      @Param(value = "info") String info);

    List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(@Param(value = "subjectId") Integer subjectId,
                                                           @Param(value = "info") String info);

}
