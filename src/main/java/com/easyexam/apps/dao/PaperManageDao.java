package com.easyexam.apps.dao;

import java.util.List;
import java.util.Map;

public interface PaperManageDao {
    /**
     * 从指定的题型数据表中返回指定难度、学科的id值
     * @param info 该map中，需要包含的key为：
     *             table:要查询的数据表
     *             level:难度等级
     *             subjectId:学科对应的id
     * @return 返回值为符合条件的所有id的List
     */
    List<Integer> findListFromQuesTable(Map info);
}
