package com.easyexam.apps.service.impl;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.config.QuestionConfig;
import com.easyexam.apps.dao.QuestionManageDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.exection.SheetNotFoundException;
import com.easyexam.apps.exection.SubjectNotFoundException;
import com.easyexam.apps.service.QuestionManageService;
import com.easyexam.apps.utils.ReadExcel;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionManageServiceImpl implements QuestionManageService {
    @Autowired
    private ReadExcel readExcel;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private QuestionConfig questionConfig;

    @Autowired
    private QuestionManageDao questionManageDao;

    @Autowired
    private CodeMsg codeMsg;

    @Override
    public List<QuesSingleChoose> findAllQuesSingleChooses(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesSingleChooses(subjectId, info, null);
    }

    @Override
    public List<QuesMultipleChoose> finAllQuesMultipleChooses(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.finAllQuesMultipleChooses(subjectId, info, null);
    }

    @Override
    public List<QuesJudge> findAllQuesJudges(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesJudges(subjectId, info, null);
    }

    @Override
    public List<QuesQuestionsAnswers> findAllQuesQuestionsAnswers(Integer subjectId, String info, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return questionManageDao.findAllQuesQuestionsAnswers(subjectId, info, null);
    }

    @Override
    public void deleteSingleChooseById(Integer id) {
        int i = questionManageDao.deleteSingleChooseById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void deleteMultipleChooseById(Integer id) {
        int i = questionManageDao.deleteMultipleChooseById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public void deleteQuesJudgeById(Integer id) {
        int i = questionManageDao.deleteQuesJudgeById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public JsonResult importQuestionFromExcel(MultipartFile file, String sheetName) {
        Date date = new Date();
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/static/tempExcel";
        String fileName = date.getTime() + ".xlsx";
        File tempFile = new File(path, fileName);
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }

        //写入完成，读取进List
        List<QuesSingleChoose> quesSingleChooses = null;
        List<QuesMultipleChoose> quesMultipleChooses = null;
        List<QuesJudge> quesJudges = null;
        List<QuesQuestionsAnswers> quesQuestionsAnswers = null;
        try {
            if ("singleChoose".equalsIgnoreCase(sheetName)) {
                quesSingleChooses = readExcel.readSingleChoose(path + "/" + fileName);
            } else if ("multipleChoose".equalsIgnoreCase(sheetName)) {
                quesMultipleChooses = readExcel.readMultipleChoose(path + "/" + fileName);
            } else if ("judge".equalsIgnoreCase(sheetName)) {
                quesJudges = readExcel.readJudge(path + fileName);
            }else if("questionsAnswers".equalsIgnoreCase(sheetName)){
                quesQuestionsAnswers = readExcel.readQuestionsAnswers(path +"/"+ fileName);
            }else if("all".equalsIgnoreCase(sheetName)){
                quesSingleChooses = readExcel.readSingleChoose(path +"/"+ fileName);
                quesMultipleChooses = readExcel.readMultipleChoose(path +"/"+ fileName);
                quesJudges = readExcel.readJudge(path +"/"+ fileName);
                quesQuestionsAnswers = readExcel.readQuestionsAnswers(path +"/"+ fileName);
            }else{
                return new JsonResult(ErrorCode.SUBJECT_ID_NOT_FOUND,codeMsg.getSubjectIdNotFound());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        } catch (SheetNotFoundException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.EXCEL_SHEET_NOT_FOUND, codeMsg.getExcelSheetNotFound());
        } catch (SubjectNotFoundException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.SUBJECT_ID_NOT_FOUND, codeMsg.getSubjectIdNotFound());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new JsonResult(ErrorCode.EXCEL_CELL_IS_NULL, codeMsg.getExcelCellIsNull());
        }

        //删除文件
        tempFile.delete();

        //写入数据库
        int count = 0;      //计数器
        int totalCount = 0; //总数计数器
        System.out.println(quesSingleChooses.isEmpty());
        try {
            if (!quesSingleChooses.isEmpty()) {
                count = questionManageDao.importQuesSingleChoose(quesSingleChooses);
                totalCount = totalCount + count;
            }
            if (!quesMultipleChooses.isEmpty()) {
                count = questionManageDao.importQuesMultipleChoose(quesMultipleChooses);
                totalCount = totalCount + count;
            }
            if (!quesJudges.isEmpty()) {
                System.out.println(quesJudges);
                count = questionManageDao.importQuesJudge(quesJudges);
                totalCount = totalCount + count;
            }
            if (!quesQuestionsAnswers.isEmpty()) {
                System.out.println(quesQuestionsAnswers);
                count = questionManageDao.importQuesQuestionsAnswers(quesQuestionsAnswers);
                totalCount = totalCount + count;
            }
        }catch (NullPointerException e){
            return new JsonResult(ErrorCode.EXCEL_CELL_IS_NULL,codeMsg.getExcelCellIsNull());
        }

        return new JsonResult(ErrorCode.EXCEL_IMPORT_SUCCESS, codeMsg.getExcelImportSuccess() + totalCount);
    }

    @Override
    public JsonResult addQuesSingleChoose(QuesSingleChoose quesSingleChoose) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getSingleChooseAnswer();
        String answer = quesSingleChoose.getAnswer();
        if (answer.length() > answerLenth || !Character.isLetter(answer.charAt(0))) {
            return new JsonResult(ErrorCode.ADD_QUES_ANSWER_WRONG, codeMsg.getAddQuesAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesSingleChoose.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for (Integer id : subjects) {
            if (id == subjectId) {
                redisContains = false;
                break;
            }
        }
        if (redisContains) {
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for (Integer id : allSubjectId) {
                redisTemplate.opsForSet().add("subject", id);
            }
            redisTemplate.expire("subject", 900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if (!mysqlContains) {
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG, codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesSingleChoose.getLevel();
        int maxLevel = questionConfig.getSingleChooseMaxLevel();
        if (level > maxLevel || level <= 0) {
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG, codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/chooseA/chooseB/chooseC/chooseD/chosoeE/
         * chooseF/answer/tag
         */
        int chooseLenth = questionConfig.getSingleChooseChoose();
        int tagLenth = questionConfig.getSingleChooseTag();
        String tag = quesSingleChoose.getTag();
        if (tag != null) {
            if (tag.length() > tagLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        //恐怖的6个选项的验证
        String chooseA = quesSingleChoose.getChooseA();
        if (chooseA == null) {
            return new JsonResult(ErrorCode.AT_LEAST_ONE_CHOOSE, codeMsg.getAtLeastOneChoose());
        } else {
            if (chooseA.length() > chooseLenth)
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
        }
        String chooseB = quesSingleChoose.getChooseB();
        String chooseC = quesSingleChoose.getChooseC();
        String chooseD = quesSingleChoose.getChooseD();
        String chooseE = quesSingleChoose.getChooseE();
        String chooseF = quesSingleChoose.getChooseF();
        List<String> chooseList = Arrays.asList(chooseB, chooseC, chooseD, chooseE, chooseF);

        for (String choose : chooseList) {
            if (choose != null) {
                if (choose.length() > chooseLenth) {
                    return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
                }
            }
        }

        //验证完成，写入数据库
        List<QuesSingleChoose> quesSingleChooses = new ArrayList<>();
        quesSingleChooses.add(quesSingleChoose);
        int i = questionManageDao.importQuesSingleChoose(quesSingleChooses);
        if (i == 1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        } else {
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public JsonResult addQuesMultipleChoose(QuesMultipleChoose quesMultipleChoose) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getMultipleChooseAnswer();
        String answer = quesMultipleChoose.getAnswer();
        if (answer.length() > answerLenth || !answer.contains("&")) {
            return new JsonResult(ErrorCode.ADD_QUES_MULTIPLE_ANSWER_WRONG, codeMsg.getAddQuesMultipleAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesMultipleChoose.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for (Integer id : subjects) {
            if (id == subjectId) {
                redisContains = false;
                break;
            }
        }
        if (redisContains) {
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for (Integer id : allSubjectId) {
                redisTemplate.opsForSet().add("subject", id);
            }
            redisTemplate.expire("subject", 900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if (!mysqlContains) {
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG, codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesMultipleChoose.getLevel();
        int maxLevel = questionConfig.getMultipleChooseMaxLevel();
        if (level > maxLevel || level <= 0) {
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG, codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/chooseA/chooseB/chooseC/chooseD/chosoeE/
         * chooseF/answer/tag
         */
        int chooseLenth = questionConfig.getMultipleChooseChoose();
        int tagLenth = questionConfig.getMultipleChooseTag();
        String tag = quesMultipleChoose.getTag();
        if (tag != null) {
            if (tag.length() > tagLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        //恐怖的6个选项的验证
        String chooseA = quesMultipleChoose.getChooseA();

        if (chooseA == null) {
            return new JsonResult(ErrorCode.AT_LEAST_ONE_CHOOSE, codeMsg.getAtLeastOneChoose());
        } else {
            if (chooseA.length() > chooseLenth)
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
        }
        String chooseB = quesMultipleChoose.getChooseB();
        String chooseC = quesMultipleChoose.getChooseC();
        String chooseD = quesMultipleChoose.getChooseD();
        String chooseE = quesMultipleChoose.getChooseE();
        String chooseF = quesMultipleChoose.getChooseF();
        List<String> chooseList = Arrays.asList(chooseB, chooseC, chooseD, chooseE, chooseF);

        for (String choose : chooseList) {
            if (choose != null) {
                if (choose.length() > chooseLenth) {
                    return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
                }
            }
        }

        //验证完成，写入数据库
        List<QuesMultipleChoose> quesMultipleChooses = new ArrayList<>();
        quesMultipleChooses.add(quesMultipleChoose);
        int i = questionManageDao.importQuesMultipleChoose(quesMultipleChooses);
        if (i == 1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        } else {
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public void deleteQuestionsAnswerById(Integer id) {
        int i = questionManageDao.deleteQuestionsAnswerById(id);
        if (i <= 0) {
            throw new MyException(ErrorCode.DELETE_QUESTION_FAIL, codeMsg.getDeleteQuesFail());
        }
    }

    @Override
    public JsonResult addQuesJudge(QuesJudge quesJudge) {
        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesJudge.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for (Integer id : subjects) {
            if (id == subjectId) {
                redisContains = false;
                break;
            }
        }
        if (redisContains) {
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for (Integer id : allSubjectId) {
                redisTemplate.opsForSet().add("subject", id);
            }
            redisTemplate.expire("subject", 900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if (!mysqlContains) {
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG, codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesJudge.getLevel();
        int maxLevel = questionConfig.getJudgeMaxLevel();
        if (level > maxLevel || level <= 0) {
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG, codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/tag
         */
        int questionLenth = questionConfig.getJudgeQuestion();
        int tagLenth = questionConfig.getJudgeTag();
        String question = quesJudge.getQuestion();
        if (question != null) {
            if (question.length() > questionLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        String tag = quesJudge.getTag();
        if (tag != null) {
            if (tag.length() > tagLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }

        //验证完成，写入数据库
        List<QuesJudge> quesJudges = new ArrayList<>();
        quesJudges.add(quesJudge);
        int i = questionManageDao.importQuesJudge(quesJudges);
        if (i == 1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        } else {
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public JsonResult addQuesQuestionsAnswers(QuesQuestionsAnswers quesQuestionsAnswers) {
        //数据合法性验证：answer
        int answerLenth = questionConfig.getQuesAnswersAnswer();
        String answer = quesQuestionsAnswers.getAnswer();
        if (answer.length() > answerLenth || !Character.isLetter(answer.charAt(0))) {
            return new JsonResult(ErrorCode.ADD_QUES_QUESANSWERS_ANSWER_WRONG, codeMsg.getAddQuesQuesAnswersAnswerWrong());
        }

        //数据合法性验证：subjectId；从redis查询，如果有则返回，如果没有则从数据库查询科目信息
        int subjectId = quesQuestionsAnswers.getSubjectId();
        Set<Integer> subjects = redisTemplate.opsForSet().members("subject");
        boolean redisContains = true;
        for (Integer id : subjects) {
            if (id == subjectId) {
                redisContains = false;
                break;
            }
        }
        if (redisContains) {
            //验证不通过，去数据库查询，并趁热写入redis
            Set<Integer> allSubjectId = questionManageDao.findAllSubjectId();
            for (Integer id : allSubjectId) {
                redisTemplate.opsForSet().add("subject", id);
            }
            redisTemplate.expire("subject", 900, TimeUnit.SECONDS);
            boolean mysqlContains = allSubjectId.contains(subjectId);
            if (!mysqlContains) {
                //不包含这个id
                return new JsonResult(ErrorCode.ADD_QUES_SUBJECT_WRONG, codeMsg.getAddQuesSubjectWrong());
            }
        }

        //数据合法性验证：level
        int level = quesQuestionsAnswers.getLevel();
        int maxLevel = questionConfig.getQuesAnswersMaxLevel();
        if (level > maxLevel || level <= 0) {
            return new JsonResult(ErrorCode.ADD_QUES_LEVEL_WRONG, codeMsg.getAddQuesLevelWrong());
        }

        /**数据合法性验证：
         * 验证字符串长度：question/tag
         */
        int questionLenth = questionConfig.getQuesAnswersQuestion();
        int tagLenth = questionConfig.getQuesAnswersTag();
        String question = quesQuestionsAnswers.getQuestion();
        if (question != null) {
            if (question.length() > questionLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }
        String tag = quesQuestionsAnswers.getTag();
        if (tag != null) {
            if (tag.length() > tagLenth) {
                return new JsonResult(ErrorCode.DATA_TOO_LONG, codeMsg.getDataTooLong());
            }
        }

        //验证完成，写入数据库
        List<QuesQuestionsAnswers> quesQuestionsAnswersList = new ArrayList<>();
        quesQuestionsAnswersList.add(quesQuestionsAnswers);
        int i = questionManageDao.importQuesQuestionsAnswers(quesQuestionsAnswersList);
        if (i == 1) {
            return new JsonResult(ErrorCode.ADD_QUES_SUCCESS, codeMsg.getAddQuesSuccess());
        } else {
            return new JsonResult(ErrorCode.SERVER_ERROR, codeMsg.getServerError());
        }
    }

    @Override
    public void updateQuestionById(Object e, Integer quesId) {
        int i = 0;
        if (quesId == 1) {
            QuesSingleChoose quesSingleChoose = (QuesSingleChoose) e;
            i = questionManageDao.updateSingleChooseById(quesSingleChoose);
        } else if (quesId == 3) {
            QuesMultipleChoose quesMultipleChoose = (QuesMultipleChoose) e;
            i = questionManageDao.updateMultipleChooseById(quesMultipleChoose);
        } else if (quesId == 4) {
            QuesJudge quesJudge = (QuesJudge) e;
            i = questionManageDao.updateQuesJudgeById(quesJudge);
        } else if (quesId == 2) {
            QuesQuestionsAnswers quesQuestionsAnswers = (QuesQuestionsAnswers) e;
            i = questionManageDao.updateQuestionsAnswerById(quesQuestionsAnswers);
        }
        if (i <= 0) {
            throw new MyException(ErrorCode.UPDATE_QUESTION_FAIL, codeMsg.getUpdateQuesFail());
        }

    }

    @Override
    public Object findQuestById(Integer id, Integer quesId) {
        if (quesId == null || id == null || quesId < 1 || quesId > 4) {
            throw new MyException(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
        }

        Object quest = null;
        if (quesId == 2) {
            quest = questionManageDao.findQuesSingleChooseById(id);
            return quest;
        } else if (quesId == 3) {
            quest = questionManageDao.findQuesMultipleChooseById(id);
            return quest;
        } else if (quesId == 4) {
            quest = questionManageDao.findQuesJudgeById(id);
            return quest;
        } else if (quesId == 1) {
            quest = questionManageDao.findQuestionsAnswerById(id);
            return quest;
        }
        if (quest == null) {
            throw new MyException(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
        }
        return new JsonResult(ErrorCode.FIND_QUESTION_FAIL, codeMsg.getFindQuesFail());
    }

    @Override
    public void addQuestPaperRedis(PaperQuestion paperQuestion, Integer uid) {

        User user = questionManageDao.findUserById(uid);
        if (user == null) {
            throw new MyException(ErrorCode.FIND_USER_ERROR, codeMsg.getFindUserError());
        }

        QuestionType questionType = questionManageDao.findQuestionType(paperQuestion.getQuestionType());

        if (questionType == null) {
            throw new MyException(ErrorCode.ADD_QUES_REDIS_FAIL, codeMsg.getAddQuesRedis());
        } else  {
            redisTemplate.opsForHash().put( user.getName() + ":" +user.getUid() + ":" + questionType.getQuestionName(), paperQuestion.getQuestionId(), paperQuestion);
        }

    }

    @Override
    public void deleteQuestToRedis(PaperQuestion paperQuestion, Integer uid) {

        User user = questionManageDao.findUserById(uid);

        if (user == null) {
            throw new MyException(ErrorCode.FIND_USER_ERROR, codeMsg.getFindUserError());
        }

        QuestionType questionType = questionManageDao.findQuestionType(paperQuestion.getQuestionType());

        if (questionType == null) {
            throw new MyException(ErrorCode.ADD_QUES_REDIS_FAIL, codeMsg.getAddQuesRedis());
        } else  {
            redisTemplate.opsForHash().delete(user.getName() + ":" +user.getUid() + ":" + questionType.getQuestionName(), paperQuestion.getQuestionId());
        }

    }

    //向前端展示购物车列表
    @Override
    public Map<String, Object> showPaperListOnRedis(Integer uid) {

        Paper paper = new Paper();
        paper.setMakeId(uid);
        List<PaperQuestion> paperQuestions = addQuestToMysql(paper, false);

        Map<String, Object> map = new LinkedHashMap<>();

        List<QuesSingleChoose> singleChooseList = new ArrayList<>();
        List<QuesMultipleChoose> multipleChooseList = new ArrayList<>();
        List<QuesJudge> judgeList = new ArrayList<>();
        List<QuesQuestionsAnswers> questionsAnswersList = new ArrayList<>();

        //遍历生成试卷的中间表
        for (PaperQuestion paperQuestion : paperQuestions) {
            if (questionManageDao.findQuestionType(paperQuestion.getQuestionType()) != null) {
                if (paperQuestion.getQuestionType() == 1) {
                    singleChooseList.add(questionManageDao.findQuesSingleChooseById(paperQuestion.getQuestionId()));
                } else if (paperQuestion.getQuestionType() == 2) {
                    multipleChooseList.add(questionManageDao.findQuesMultipleChooseById(paperQuestion.getQuestionId()));
                } else if (paperQuestion.getQuestionType() == 3) {
                    judgeList.add(questionManageDao.findQuesJudgeById(paperQuestion.getQuestionId()));
                } else if (paperQuestion.getQuestionType() == 4) {
                    questionsAnswersList.add(questionManageDao.findQuestionsAnswerById(paperQuestion.getQuestionId()));
                } else {
                    throw new MyException(ErrorCode.SHOW_QUES_LIST_REDIS_FAIL, codeMsg.getShowQuesListOnRedis());
                }

            }
        }

        map.put("QuesSingleChoose", singleChooseList);
        map.put("QuesMultipleChoose", multipleChooseList);
        map.put("QuesJudge", judgeList);
        map.put("QuesQuestionsAnswers", questionsAnswersList);

        return map;
    }

    @Override
    public List<PaperQuestion> addQuestToMysql(Paper paper, boolean bo) {
        User user = questionManageDao.findUserById(paper.getMakeId());
        if (user == null) {
            throw new MyException(ErrorCode.FIND_USER_ERROR, codeMsg.getFindUserError());
        }

        List<QuestionType> questionTypes = questionManageDao.findQuestionTypes();

        if (questionTypes == null) {
            throw new MyException(ErrorCode.ADD_QUES_REDIS_FAIL, codeMsg.getAddQuesRedis());
        }

        //临时集合用来保存键和值
        List<Integer> quesList = new ArrayList<>();
        List<PaperQuestion> paperQuestionsList = new ArrayList<>();

        for (QuestionType questionType : questionTypes) {
            LinkedHashMap<Integer, PaperQuestion> quesSingleChooseMap = (LinkedHashMap<Integer, PaperQuestion>) redisTemplate.opsForHash().entries(user.getName() + ":" +user.getUid() + ":" + questionType.getQuestionName());
            for (Map.Entry<Integer, PaperQuestion> vo : quesSingleChooseMap.entrySet()) {
                quesList.add(vo.getKey());
                paperQuestionsList.add(vo.getValue());
            }
        }

        //遍历Redis上的各种题型
        LinkedHashMap<Integer, PaperQuestion> quesSingleChooseMap = (LinkedHashMap<Integer, PaperQuestion>) redisTemplate.opsForHash().entries(user.getName() + user.getUid() + ":" + "QuesSingleChoose");
        for (Map.Entry<Integer, PaperQuestion> vo : quesSingleChooseMap.entrySet()) {
            quesList.add(vo.getKey());
            paperQuestionsList.add(vo.getValue());
        }

        for (PaperQuestion paperQuestion : paperQuestionsList) {
            System.out.println(paperQuestion);
        }

        if (bo) {

            int i1 = questionManageDao.autoCreatePaper(paper);
            if (i1 <= 0) {
                throw new MyException(ErrorCode.AUTO_CREATE_PAPER_FAIL, codeMsg.getAutoCreatePaper());
            }

            int id = paper.getId();

            for (PaperQuestion paperQuestion : paperQuestionsList) {
                paperQuestion.setPaperId(id);
            }

            int i = paperQuestionsList.size();

            int j = questionManageDao.addPaperToMySql(paperQuestionsList);

            if (j != i) {
                throw new MyException(ErrorCode.AUTO_CREATE_PAPER_FAIL, codeMsg.getAutoCreatePaper());
            }
        }

        return paperQuestionsList;
    }

}
