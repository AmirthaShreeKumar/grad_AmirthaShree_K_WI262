package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Student;
import com.example.demo.repos.db1.StudentRepoDB1;
import com.example.demo.repos.db2.StudentRepoDB2;

@Service
public class StudentService {

    @Autowired
    private StudentRepoDB1 repo1;

    @Autowired
    private StudentRepoDB2 repo2;

    public void saveStudent(Student s) {
        repo1.save(s);   // DB1
        repo2.save(s);   // DB2
    }
}
