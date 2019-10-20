package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.*;
import com.easyexam.apps.service.StudentManageService;
import com.easyexam.apps.service.StudentService;
import com.easyexam.apps.utils.CandidateNumberMaker;
import com.easyexam.apps.utils.IDCardVerify;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private ErrorCode errorCode;
    @Autowired
    private CandidateNumberMaker candidateNumberMaker;
    @Autowired
    private StudentService studentService;
    @Autowired
    private IDCardVerify idCardVerify;

    //查找所有的考场信息
    @RequestMapping(value = "/Examinee/findAll", method = RequestMethod.GET)
    public Map findAllExaminee(Integer page, Integer limit) {
        HashMap<String, Object> map = new HashMap<>();
        List<Student> ExamineeList = studentManageService.findAllExaminee(page, limit);
        map.put("count", ((Page) ExamineeList).getTotal());
        map.put("data", ExamineeList);
        map.put("code", 0);
//        System.out.println();
        return map;
    }

    @RequestMapping(value = "/Examinee/findOne")
    public JsonResult findOneExaminee(Integer id) {
        Student student = new Student();

        if (id == null) {
            return new JsonResult(2400, codeMsg.getStudentFindoneError());
        } else if (id != student.getId()) {
            return new JsonResult(4001, codeMsg.getStudentNotFindone());
        }
        StudentPaper oneExaminee = studentManageService.findOneExaminee(id);
        System.out.println(oneExaminee);
        return new JsonResult(1019, codeMsg.getStudentFindoneSuccess());
    }

    @PostMapping("/Examinee/update")
    public JsonResult updateExaminee(Student student, String repeatPassword) {
        int id = student.getId();
        String phone = student.getPhone();
        String password = student.getPassword();
        List<Student> allExamineeId = studentManageService.findAllExamineeId();
        Integer id1 = null;
        String phone1 = null;
        if (!password.equals(repeatPassword)) {
            return new JsonResult(2405, codeMsg.getExamineeDoublePasswordSame());
        }
        for (Student student1 : allExamineeId) {
            id1 = student1.getId();
            phone1 = student1.getPhone();
            if (phone1.equals(phone)) {
                return new JsonResult(2401, codeMsg.getStudentphoneError());
            }
        }
        studentManageService.updateExaminee(student);
        return new JsonResult(1005, codeMsg.getStudentUpdateSuccess());

    }

    @PostMapping("/Examinee/add")
    public JsonResult addExaminee(Student student) {
        String phone = student.getPhone();
        String idCard = student.getIdCard();
        StudentRole studentRole = new StudentRole();
        List<Student> allExamineeId = studentManageService.findAllExamineeId();

        String stuphone = null;
        String stuidCard = null;
        for (Student stu : allExamineeId) {
            stuphone = stu.getPhone();
            stuidCard = stu.getIdCard();

            if (phone.equals(stuphone)) {
                return new JsonResult(2401, codeMsg.getStudentphoneError());
            }
            if (idCard.equals(stuidCard)) {
                return new JsonResult(2402, codeMsg.getStudentidCardError());

            }
        }

        boolean verify = idCardVerify.verify(student.getIdCard(), student.getSex());
        if (verify == false) {
            return new JsonResult(2403, codeMsg.getStudentIdCardFormatError());

        }

        int idNumber = candidateNumberMaker.getCandidateNumber();
        student.setCandidateNumber(idNumber);

        studentManageService.addExaminee(student);
        studentRole.setStudentId(student.getId());
        studentRole.setRoleId(3);
        studentManageService.addExamineeRole(studentRole);
        return new JsonResult(1006, codeMsg.getStudentaddSuccess());

    }

    @PostMapping("/ExamineeJoinExam/add")
    public JsonResult addExamineeJoinExam(String beginTime,String endTime,String roomName, Integer rid, Integer sid) {

            studentManageService.addExamineeJoinExam(beginTime,endTime,roomName, sid, rid);
        return new JsonResult(1007, codeMsg.getAddExamineeRoomSuccess());


    }

    //当前端页面传过来的的String类型的日期与后台实体类的Date类型不匹配时，需要加上该方法
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));

    }


}
