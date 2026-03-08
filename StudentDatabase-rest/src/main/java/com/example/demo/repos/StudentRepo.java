package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    // -----------------------------------------
    // Find all students of a school
    List<Student> findBySchool(String school);

    // -----------------------------------------
    // Count students in a school
    long countBySchool(String school);

    // -----------------------------------------
    // Count students in a standard/class
    long countByStandard(String standard);

    // -----------------------------------------
    // Pass students (>= 40%) sorted desc
    List<Student> findByPercentageGreaterThanEqualOrderByPercentageDesc(double percentage);

    // -----------------------------------------
    // Fail students (< 40%) sorted desc
    List<Student> findByPercentageLessThanOrderByPercentageDesc(double percentage);

    // -----------------------------------------
    // Count by gender and standard
    long countByGenderAndStandard(String gender, String standard);

}
