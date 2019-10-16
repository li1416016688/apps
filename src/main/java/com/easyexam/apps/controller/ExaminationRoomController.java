package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExaminationRoomController {
    @Autowired
    private ExaminationRoomService examinationRoomService;
    @Autowired
    private CodeMsg codeMsg;
    @PostMapping("/examinationRoom/findAll")
    public Map findAllexaminationRoom(Integer subjectId, Integer invigilateId , Integer paperId, String info, Integer page, Integer limit){
        HashMap<String, Object> map = new HashMap<>();
        List<ExaminationRoom> examinationRoomList = examinationRoomService.findAllExaminationRoom(subjectId, invigilateId, paperId, info,page, limit);
        map.put("count", ((Page)examinationRoomList).getTotal());
        map.put("data", examinationRoomList);
        map.put("code", 1015);
        return map;
    }
    @PostMapping("examinationRoom/findone")
    public JsonResult findOneExaminationRoom(Integer id){
        ExaminationRoom oneExaminationRoom = examinationRoomService.findOneExaminationRoom(id);
        return new JsonResult(1016,codeMsg.getFindoneExaminationroom());
    }
    @PostMapping("examinationRoom/update")
    public JsonResult updateExaminationRoom(ExaminationRoom room){
        examinationRoomService.updateExaminationRoom(room);
        return new JsonResult(1017,codeMsg.getFindoneExaminationroom());
    }
    @PostMapping("examinationRoom/delete")
    public JsonResult deleteExaminationRoom(Integer id){

        examinationRoomService.deleteExaminationRoom(id);
        return new JsonResult(1018,codeMsg.getDeleteExaminationroom());
    }
}
