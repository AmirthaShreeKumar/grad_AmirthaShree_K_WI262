package com.example.demo.repos.db2;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Student;

public interface StudentRepoDB2 extends JpaRepository<Student, Integer> 
{}
