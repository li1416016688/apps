package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.pojo.RandomPaper;

public interface PaperManageService {
    JsonResult createRandomPaper(RandomPaper randomPaper,int makeId);

    JsonResult deletePaper(int id);
}
