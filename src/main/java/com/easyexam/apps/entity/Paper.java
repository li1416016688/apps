/**
 * 试卷信息，通常用paper表示
 */
package com.easyexam.apps.entity;

import lombok.Data;

@Data
public class Paper {
    private int id;
    private int subjectId;  //科目id
    private int Subject;
    private int checkId;    //改卷人id，对应User表主键
    private User checker;
    private int makeId;     //生成人id，对应User表主键
    private User maker;
}
