/**
 * 该工具类根据输入的数量和难度随机抽取数据库中的题目id值，并将id封装成list进行返回
 * 针对每个题型都有一个方法，方法的执行过程相同，查询的数据库不同
 */
package com.easyexam.apps.utils;

import com.easyexam.apps.dao.PaperManageDao;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RandomQues {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    QuestionManageDao questionManageDao;
    @Autowired
    PaperManageDao paperManageDao;

    //questype，对应数据表q_question_type，用于寻找该题型的数据表名
    private static int SINGLECHOOSE_QUESTYPE = 1;
    private static int MULTIPLECHOOSE_QUESTYPE = 2;
    private static int JUDGE_QUESTYPE = 3;
    private static int QUESTIONSANSWERS_QUESTYPE = 4;

    //获取单选题quesType == 1
    public List<Integer> getSingleChoose(List<Integer> questionCount, int subjectId, int level){
        Integer count = questionCount.get(SINGLECHOOSE_QUESTYPE - 1);   //将其questype-1获取索引值
        List<Integer> allQuesIds = getQuesIdFromSql(subjectId, SINGLECHOOSE_QUESTYPE, level);
        List<Integer> randomQuesId = getRandomQuesId(allQuesIds, count);
        return randomQuesId;
    }

    //获取多选题quesType ==2
    public List<Integer> getMultipleChoose(List<Integer> questionCount, int subjectId, int level){
        Integer count = questionCount.get(MULTIPLECHOOSE_QUESTYPE - 1);   //将其questype-1获取索引值
        List<Integer> allQuesIds = getQuesIdFromSql(subjectId, MULTIPLECHOOSE_QUESTYPE, level);
        List<Integer> randomQuesId = getRandomQuesId(allQuesIds, count);
        return randomQuesId;
    }

    //获取判断题quesType == 3
    public List<Integer> getJudge(List<Integer> questionCount, int subjectId, int level){
        Integer count = questionCount.get(JUDGE_QUESTYPE - 1);   //将其questype-1获取索引值
        List<Integer> allQuesIds = getQuesIdFromSql(subjectId, JUDGE_QUESTYPE, level);
        List<Integer> randomQuesId = getRandomQuesId(allQuesIds, count);
        return randomQuesId;
    }

    //获取问答题quesType == 4
    public List<Integer> getQuestionsAnswers(List<Integer> questionCount, int subjectId, int level){
        Integer count = questionCount.get(QUESTIONSANSWERS_QUESTYPE - 1);   //将其questype-1获取索引值
        List<Integer> allQuesIds = getQuesIdFromSql(subjectId, QUESTIONSANSWERS_QUESTYPE, level);
        List<Integer> randomQuesId = getRandomQuesId(allQuesIds, count);
        return randomQuesId;
    }

    /**
     * 对传入的题目id随机采样，然后返回一个被抽中的List集合;
     * 如果采样数量(count)大于传入List的size，则将List全部返回;
     * 建议使用该方法前对传入的参数进行判断，避免因为传入的List.size小于count
     * 导致的生成的题目数量不足的情况;
     * @param QuesIds 这里输入的题目id List应该是已经确定好难度的，该方法中
     *                并不能区分难度，只能从输入的List中获取
     * @return
     */
    private List<Integer> getRandomQuesId(List<Integer> QuesIds,int count){
        List<Integer> selectedIds = new ArrayList<>();
        //输入的List比需要的id数量小，只好全部返回
        if(QuesIds.size() <= count){
            return QuesIds;
        }
        //循环随机放入
        for(int i = 0; i < count; i++) {
            int index = (int) (Math.random() * QuesIds.size());
            Integer nativeId = QuesIds.get(index);
            if(selectedIds.contains(nativeId)){
                i--;
            }else {
                selectedIds.add(QuesIds.get(index));
            }
        }
        return selectedIds;
    }

    /**
     * 根据传入的难度等级(level)和学科id(subjectId)以及题型(quesType)
     * 从mysql中查询获取符合指定难度的题目id List;
     * @param subjectId  学科信息，传入的是学科的id值
     * @param quesType  传入的是题目类型，依据该类型可以从数据表q_question_type中获取该题型对应存储的数据表名
     * @param level  难度等级，1~5
     * @return
     */
    private List<Integer> getQuesIdFromSql(int subjectId,int quesType, int level){
        QuestionType questionType = questionManageDao.findQuestionType(quesType);
        String tableName = questionType.getQuestionTableName();
        Map info = new HashMap<>();
        info.put("table",tableName);
        info.put("level",level);
        info.put("subjectId",subjectId);
        List<Integer> ids = paperManageDao.findListFromQuesTable(info);
        return ids;
    }
}
