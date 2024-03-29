package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin
@Controller
public class ExaminationRoomController {
    @Autowired
    private ExaminationRoomService examinationRoomService;
    @Autowired
    private CodeMsg codeMsg;

    /**
     * 跳转页面  考场管理
     * @return
     */

    @RequestMapping("/Examinationlist")
    public String ExaminationRoomList() {
        return "room";
    }
    @RequestMapping("/Examineelist")
    public String ExamineeList() {
        return "student";
    }

    @RequestMapping("/studentExamination")
    public String studentExamination(){
        return "sturoom";
    }



    //查找所有的考场信息
//    @RequiresPermissions({"room:list"})
    @RequestMapping(value = "/examinationRoom/findAll")
    @ResponseBody
    public Map findAllexaminationRoom(Integer page, Integer limit){
        HashMap<String, Object> map = new HashMap<>();
        List<ExaminationRoom> examinationRoomList = examinationRoomService.findAllExaminationRoom(page, limit);
        map.put("msg","");
        map.put("count", ((Page)examinationRoomList).getTotal());
        map.put("data", examinationRoomList);
        map.put("code", 0);
        return map;
    }



    //查找一个考场的信息
    @PostMapping("/examinationRoom/findone")
    @ResponseBody
    public JsonResult findOneExaminationRoom(Integer id){
        ExaminationRoom oneExaminationRoom = examinationRoomService.findOneExaminationRoom(id);
        return new JsonResult(1016,codeMsg.getFindoneExaminationroom());
    }
    //修改一个考场的信息
    @PostMapping("/examinationRoom/update")
    @ResponseBody
    public JsonResult updateExaminationRoom(ExaminationRoom room,String subjectName,String paperName,String invigilateName){
        examinationRoomService.updateExaminationRoom( room,subjectName,paperName,invigilateName);
        return new JsonResult(1017,codeMsg.getUpdateExaminationroom());
    }

//    删除一个考场的信息
    @PostMapping("/examinationRoom/delete")
    @ResponseBody
    public JsonResult deleteExaminationRoom(Integer id){
        examinationRoomService.deleteExaminationRoom(id);
        return new JsonResult(1018,codeMsg.getDeleteExaminationroom());
    }

//    增加一个考场的信息

    @PostMapping("/examinationRoom/add")
    @ResponseBody
    public JsonResult addExaminationRoom(ExaminationRoom room,String subjectName,String paperName,String invigilateName) {
        examinationRoomService.addExaminationRoom( room,subjectName,paperName,invigilateName);
        return new JsonResult(1419,codeMsg.getDeleteExaminationroom());
    }
}
