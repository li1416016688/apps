package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.service.ExaminationRoomService;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ExaminationRoomController {
    @Autowired
    private ExaminationRoomService examinationRoomService;
    @Autowired
    private CodeMsg codeMsg;

    //查找所有的考场信息
    @RequiresPermissions({"room:list"})
    @RequestMapping(value = "/examinationRoom/findAll" ,method = RequestMethod.GET)
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
    @PostMapping("examinationRoom/findone")
    public JsonResult findOneExaminationRoom(Integer id){
        ExaminationRoom oneExaminationRoom = examinationRoomService.findOneExaminationRoom(id);
        return new JsonResult(1016,codeMsg.getFindoneExaminationroom());
    }
    //修改一个考场的信息
    @PostMapping("examinationRoom/update")
    public JsonResult updateExaminationRoom(ExaminationRoom room){
        examinationRoomService.updateExaminationRoom(room);
        return new JsonResult(1017,codeMsg.getFindoneExaminationroom());
    }

//    删除一个考场的信息
    @PostMapping("examinationRoom/delete")
    public JsonResult deleteExaminationRoom(Integer id){

        examinationRoomService.deleteExaminationRoom(id);
        return new JsonResult(1018,codeMsg.getDeleteExaminationroom());
    }

//    增加一个考场的信息

    @PostMapping("examinationRoom/add")
    public JsonResult addExaminationRoom(ExaminationRoom room) {

        examinationRoomService.addExaminationRoom(room);
        return new JsonResult(1419,codeMsg.getDeleteExaminationroom());
    }
}
