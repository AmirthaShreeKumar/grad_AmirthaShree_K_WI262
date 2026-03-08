package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Employee;

public interface EmpRepo extends JpaRepository<Employee,Integer> {

    List<Employee> findBySalaryLessThan(double salary);

    List<Employee> findByAgeGreaterThan(int age);

    List<Employee> findByDesignationIgnoreCase(String desig);

    @Query("FROM Employee where designation = ?1 order by salary desc")
    List<Employee> myCustomQuery(String desig);

}