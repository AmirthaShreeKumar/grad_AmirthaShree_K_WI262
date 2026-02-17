package com.example.demo.repos.db1;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Student;

public interface StudentRepoDB1 extends JpaRepository<Student, Integer> 
{
	
}
