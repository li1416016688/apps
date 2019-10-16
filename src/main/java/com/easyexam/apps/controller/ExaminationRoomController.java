package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExaminationRoomController {
    @Autowired
    private ExaminationRoomService examinationRoomService;
    @Autowired
    private CodeMsg codeMsg;
    @PostMapping("examinationRoom/findAll")
    public JsonResult findAllexaminationRoom(){
        List<ExaminationRoom> examinationRoomList = examinationRoomService.findAllExaminationRoom();
        return new JsonResult(1015,codeMsg.getFindExaminationroom());
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
        return new JsonResult(1017,codeMsg.getDeleteExaminationroom());
    }
}
