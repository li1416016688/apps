package com.easyexam.apps.dao;

import com.easyexam.apps.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentManageDao {

public List<Student> findAllExaminee();


}
