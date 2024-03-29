package com.easyexam.apps.controller;

import com.easyexam.apps.common.CodeMsg;
import com.easyexam.apps.common.ErrorCode;
import com.easyexam.apps.common.JsonResult;
import com.easyexam.apps.entity.Complexity;
import com.easyexam.apps.entity.Student;
import com.easyexam.apps.entity.Subject;
import com.easyexam.apps.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController {
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private StudentService studentService;


//    @RequestMapping("/loginStudent")
//    public String studentLogin() {
//        return "student_login";
//    }

    @RequestMapping(value = "student/login")
    @ResponseBody
    public JsonResult studentLogin(String idCard, String password, HttpSession session){
        Student student = studentService.studentLogin(idCard, password);
        session.setAttribute("student",student);
        return new JsonResult(ErrorCode.SUCCESS,student);
    }

    @RequestMapping("registerStudent")
    public String studentRegister() {
        return "student_register";
    }

    @RequestMapping("student/register")
    @ResponseBody
    public JsonResult studentRegister(Student student){
        System.out.println("=="+student);
        studentService.studentRegister(student);
        return new JsonResult(ErrorCode.SUCCESS,codeMsg.getSuccess());
    }

    @RequestMapping("student/subject")
    @ResponseBody
    public JsonResult studentSubject(){
        List<Subject> allSubject = studentService.findAllSubject();
        return new JsonResult(ErrorCode.SUCCESS,allSubject);
    }
    @RequestMapping("subject/complexity")
    @ResponseBody
    public JsonResult subjectComplexity(){
        List<Complexity> allComplexity = studentService.findAllComplexity();
        return new JsonResult(ErrorCode.SUCCESS,allComplexity);
    }

    @RequestMapping("/createPracticePaper.do")
    @ResponseBody
    public JsonResult createPaper(Integer subjectId, Integer level, Integer num1,
                            Integer num2, Integer num3, Integer num4){
        Map<String, List<Object>> map = studentService.createPaper(subjectId, level, num1, num2, num3, num4);
        return new JsonResult(ErrorCode.SUCCESS,map);
    }


}
