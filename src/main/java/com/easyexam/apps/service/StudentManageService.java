    package com.easyexam.apps.service;

    import com.easyexam.apps.entity.Student;
    import com.easyexam.apps.entity.StudentPaper;
    import com.easyexam.apps.entity.StudentRole;

    import java.util.List;

    public interface StudentManageService {

        public List<Student> findAllExaminee(Integer page, Integer limit);

        public List<Student> findAllExamineeId();
        public StudentPaper findOneExaminee(Integer id);

        public void updateExaminee(Student student);
        public void addExaminee(Student student);
        public void addExamineeRole(StudentRole studentRole);


    }
