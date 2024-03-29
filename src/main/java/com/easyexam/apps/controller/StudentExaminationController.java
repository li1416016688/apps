package com.easyexam.apps.controller;

import com.alibaba.fastjson.JSONObject;
import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.dao.StudentDao;
import com.easyexam.apps.dao.StudentExaminationDao;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StudentExaminationController {

    @Autowired
    private StudentExaminationService studentExaminationService;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentExaminationDao studentExaminationDao;

    /**
     * @param subjectId  非必要传参，传参表示哪个学科，目前，1表示语文，2表示数学，3表示英语
     * @param roomName 非必要传参，这是具体考场信息的，具体时间，生成该卷子的老师
     * @return
     */
    @RequestMapping("showExamination.do")
    public JsonResult showExamination(Integer subjectId, String roomName){
        List<ExaminationRoom> allExaminationRoom = studentExaminationService.findAllExaminationRoom(subjectId,roomName);
        return new JsonResult(ErrorCode.SUCCESS,allExaminationRoom);
    }

    /**生成试卷，把完整的试卷放在redis，取出时，把答案除去，
     * @param paperId 试卷的id,必填项
     * @param idCard 学生的身份证号，必填项
     * @return
     */
    @RequestMapping("createPaper.do")
    public JsonResult createPaper(Integer paperId, String idCard){
        //根据id查询用户
        Student student = studentDao.studentLogin(idCard);
        if (student == null){
            throw new MyException(ErrorCode.ILLEGAL_STUDENT,codeMsg.getIllegalStudent());
        }

        Object opaper = redisTemplate.opsForHash().get("easyexam","paperId"+paperId );
        if(opaper != null){
            StudentPaper paper1 = (StudentPaper)opaper;
            JSONObject paperQues1 = paper1.getPaperQues();
            //json对象转化为java对象
            PaperQues paperQues = JSONObject.toJavaObject(paperQues1, PaperQues.class);
            //将paperQues里面的四个类型的题的答案设置为空
            List<QuesSingleChoose> quesSingleChooses = paperQues.getQuesSingleChooses();
            for (int i = 0; i < quesSingleChooses.size() ; i++) {
                quesSingleChooses.get(i).setAnswer("");
            }
            paperQues.setQuesSingleChooses(quesSingleChooses);
            List<QuesMultipleChoose> quesMultipleChooses = paperQues.getQuesMultipleChooses();
            for (int i = 0; i < quesMultipleChooses.size(); i++) {
                quesMultipleChooses.get(i).setAnswer("");
            }
            paperQues.setQuesMultipleChooses(quesMultipleChooses);
            List<QuesJudge> quesJudges = paperQues.getQuesJudges();
            for (int i = 0; i < quesJudges.size(); i++) {
                quesJudges.get(i).setAnswer(-1);
            }
            paperQues.setQuesJudges(quesJudges);
            List<QuesQuestionsAnswers> quesQuestionsAnswers = paperQues.getQuesQuestionsAnswers();
            for (int i = 0; i < quesQuestionsAnswers.size(); i++) {
                quesQuestionsAnswers.get(i).setAnswer("");
            }
            paperQues.setQuesQuestionsAnswers(quesQuestionsAnswers);
            paper1.setPaperQues((JSONObject) JSONObject.toJSON(paperQues));
            //将学生信息添加进去，返回数据
            paper1.setStudent(student);
            return new JsonResult(ErrorCode.SUCCESS,paper1);
        }else {
            StudentPaper paper1 = studentExaminationService.createPaper(paperId, idCard);
            redisTemplate.opsForHash().put("easyexam","paperId"+paperId,paper1);

            //设置完之后在存在redis里面，再次获取，把答案剔除
            Object opaper2 = redisTemplate.opsForHash().get("easyexam","paperId"+paperId );
            StudentPaper paper2 = (StudentPaper)opaper2;
            JSONObject paperQues1 = paper2.getPaperQues();
            //json对象转化为java对象
            PaperQues paperQues = JSONObject.toJavaObject(paperQues1, PaperQues.class);
            //将paperQues里面的四个类型的题的答案设置为空
            List<QuesSingleChoose> quesSingleChooses = paperQues.getQuesSingleChooses();
            for (int i = 0; i < quesSingleChooses.size() ; i++) {
                quesSingleChooses.get(i).setAnswer("");
            }
            paperQues.setQuesSingleChooses(quesSingleChooses);
            List<QuesMultipleChoose> quesMultipleChooses = paperQues.getQuesMultipleChooses();
            for (int i = 0; i < quesMultipleChooses.size(); i++) {
                quesMultipleChooses.get(i).setAnswer("");
            }
            paperQues.setQuesMultipleChooses(quesMultipleChooses);
            List<QuesJudge> quesJudges = paperQues.getQuesJudges();
            for (int i = 0; i < quesJudges.size(); i++) {
                quesJudges.get(i).setAnswer(-1);
            }
            paperQues.setQuesJudges(quesJudges);
            List<QuesQuestionsAnswers> quesQuestionsAnswers = paperQues.getQuesQuestionsAnswers();
            for (int i = 0; i < quesQuestionsAnswers.size(); i++) {
                quesQuestionsAnswers.get(i).setAnswer("");
            }
            paperQues.setQuesQuestionsAnswers(quesQuestionsAnswers);
            paper2.setPaperQues((JSONObject) JSONObject.toJSON(paperQues));
            //将学生信息添加进去，返回数据
            paper2.setStudent(student);
            return new JsonResult(ErrorCode.SUCCESS,paper2);
        }
    }

    /**
     * 时刻保存学生答案到redis里面
     * @param paperId 试卷的id
     * @param idCard 学生身份证号
     * @param map 考生答案
     */
    @RequestMapping("momentSaveStudentAnswer.do")
    public void momentSaveStudentAnswer(Integer paperId, String idCard, Map<String,List<String>> map){
        //将考生答案一直保存到redis里面，
        redisTemplate.opsForHash().put("easyexam","idCard"+idCard+paperId,map);
    }



    /**最后交卷，两种方式，时间到了强制交卷，考生自己交卷
     * 考生答案和试卷信息都是从redis获取，同时获取后将考生答案这个键设置一个月的过期时间，以便保存失败，还能从redis获取
     * @param paperId  试卷的id
     * @param idCard 考生的身份证号
     */
    @RequestMapping("saveStudentPaper" )
    public JsonResult saveStudentPaper(Integer paperId, String idCard){
        //在redis取出当时开始考试生成的试卷，含有答案，不含有考生信息，含有考场信息
        Object opaper2 = redisTemplate.opsForHash().get("easyexam","paperId"+paperId );
        StudentPaper paper2 = (StudentPaper)opaper2;
        //考生答案
        Map<String,List<String>> studentAnswer = (Map<String,List<String>>)redisTemplate.opsForHash().get("easyexam", "idCard" + idCard+paperId);
        //将考生答案和试卷加在一起放进，mysql里面以json格式存储
        StudentPaper studentPaper = studentExaminationService.saveStudentPaper(paperId, idCard, studentAnswer);
        if (studentPaper == null){
            return new JsonResult(2507,"考生保存试卷失败");
        }else {
            return new JsonResult(ErrorCode.SUCCESS,codeMsg.getSuccess());
        }
    }

}
