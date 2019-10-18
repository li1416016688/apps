package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.ExaminationRoom;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.StudentPaper;
import com.easyexam.apps.entity.User;
import com.easyexam.apps.exection.MyException;
import com.easyexam.apps.service.StudentManageService;
import com.github.pagehelper.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
@ResponseBody
public class StudentManageController {
    @Autowired
    private StudentManageService studentManageService;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired(required = false)
    private ErrorCode errorCode;

    //查找所有的考场信息
    @RequestMapping(value = "/Examinee/findAll" ,method = RequestMethod.GET)
    public Map findAllExaminee(Integer page, Integer limit){
        HashMap<String, Object> map = new HashMap<>();
        List<Student> ExamineeList = studentManageService.findAllExaminee(page, limit);
        map.put("count", ((Page)ExamineeList).getTotal());
        map.put("data", ExamineeList);
        map.put("code", 0);
//        System.out.println();
        return map;
    }
    @RequestMapping(value = "/Examinee/findOne")
    public JsonResult findOneExaminee(Integer id){
        Student student = new Student();

        if (id==null){
            return new JsonResult(2400,codeMsg.getStudentFindoneError());
        }else if (id!=student.getId()){
            return new JsonResult(4001,codeMsg.getStudentNotFindone());
        }
        StudentPaper oneExaminee = studentManageService.findOneExaminee(id);
        System.out.println(oneExaminee);
        return new JsonResult(1019,codeMsg.getStudentFindoneSuccess());
    }
        @PostMapping("/Examinee/update")
    public JsonResult updateExaminee(Student student){
            int id = student.getId();
            if ("".equals(id)){
                return new JsonResult(2400,codeMsg.getStudentFindoneError());
            }
        studentManageService.updateExaminee(student);
        return new JsonResult(1005,codeMsg.getStudentUpdateSuccess());
    }
//    @PostMapping("/Examinee/update")
//    public JsonResult updateExaminee(Student student){
//        int id = student.getId();
//        String phone = student.getPhone();
//        List<Student> allExamineeId =studentManageService.findAllExamineeId();
//        Integer id1=null;
//        String phone1=null;
//
//        for (Student student1:allExamineeId){
//            id1=student1.getId();
//            phone1=student1.getPhone();
//        }
//        if ("".equals(id)){
//            return new JsonResult(2400,codeMsg.getStudentFindoneError());
//
//        } else if (id!=id1){
//            return new JsonResult(4001,codeMsg.getStudentNotFindone());
//
//        }else if(phone==phone1){
//            return  new JsonResult(2401,codeMsg.getStudentphoneError());
//        }
//        studentManageService.updateExaminee(student);
//        return new JsonResult(1005,codeMsg.getStudentUpdateSuccess());
//    }




}
