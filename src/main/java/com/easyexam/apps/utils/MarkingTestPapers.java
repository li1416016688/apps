/**
 * 给出客观题的分数，需要传入考生答案和正确答案以及该题的分值
 */

package com.easyexam.apps.utils;

import org.springframework.stereotype.Component;

@Component
public class MarkingTestPapers {
    /**
     * 批改单选题的方法
     * @param realAnswer  真正的答案
     * @param studentAnswer  学生答案
     * @param score  该题分值
     * @return  回答正确返回score分，回答错误返回0分；如返回-1，则输入的正确答案或score存在问题！
     */
    public int markingSingleChoice(String realAnswer, String studentAnswer, int score){
        //异常及白卷判断
        if(studentAnswer == null || "".equals(studentAnswer))
            return 0;
        if(realAnswer == null || "".equals(realAnswer) || score <= 0)
            return -1;

        //正确性判断
        if(realAnswer.equalsIgnoreCase(studentAnswer)){
            return score;
        }else {
            return 0;
        }
    }

    /**
     * 批改多选题的方法
     * @param realAnswer  真正的答案，答案格式如：a&b&c
     * @param studentAnswer  学生答案，答案格式如：a&b&c
     * @param score  该题的分值
     * @return  如果多选、错选得分为0；完全选对得分score分；少选得分为(score/2)向下取整;
     *          如返回-1，则输入的正确答案或score存在问题！
     */
    public int markingMultipleChoice(String realAnswer, String studentAnswer, int score){
        //异常及白卷判断
        if(studentAnswer == null || "".equals(studentAnswer))
            return 0;
        if(realAnswer == null || "".equals(realAnswer) || score <= 0)
            return -1;

        //正确性判断
        realAnswer = realAnswer.toUpperCase();
        studentAnswer = studentAnswer.toUpperCase();
        String[] studentAnswers = studentAnswer.split("&");
        for(int i = 0; i < studentAnswers.length; i++){
            if(!realAnswer.contains(studentAnswers[i])){
                return 0;   //学生答案中发现有正确答案不包含的选项，多选或错选，得0分；
            }
        }
        if(realAnswer.equals(studentAnswer)){
            return score;
        }else{
            return score / 2;
        }
    }

    /**
     * 批改判断题的方法
     * @param realAnswer  真正的答案，传入boolean类型
     * @param studentAnswer  学生的答案，传入boolean类型
     * @param score  该题分值
     * @return  如果学生答案正确得分score，否则得分0；如返回-1，则输入的正确答案或score存在问题！
     */
    public int markingJudge(int realAnswer, int studentAnswer, int score){
        //异常及白卷判断
        if(score <= 0){
            return -1;
        }
        int bStudentAnswer;
        try{
            bStudentAnswer = (int) studentAnswer;
        } catch (ClassCastException e){
            return 0;   //学生交了白卷
        }

        //正确性判断
        if(realAnswer == studentAnswer){
            return score;
        } else {
            return 0;
        }
    }
}
