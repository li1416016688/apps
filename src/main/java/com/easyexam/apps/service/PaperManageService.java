package com.easyexam.apps.service;

import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.pojo.RandomPaper;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaperManageService {
    public JsonResult createRandomPaper(RandomPaper randomPaper);
}
