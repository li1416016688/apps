/**
 * 试卷信息，通常用paper表示
 */
package com.easyexam.apps.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Paper implements Serializable {
    private int id;
    private String name;
    private Date createTime;
    private Integer checkId;    //改卷人id，对应User表主键
    private int makeId;     //生成人id，对应User表主键
    private int subjectId;  //科目id
    private int level;

    private User checker;
    private User maker;

}
