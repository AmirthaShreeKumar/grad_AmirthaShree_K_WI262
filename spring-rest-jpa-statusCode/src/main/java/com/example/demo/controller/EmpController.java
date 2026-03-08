package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Employee;
import com.example.demo.repo.EmpRepo;

@RestController
public class EmpController {

    @Autowired
    EmpRepo empRepo;

    @GetMapping("/greet")
    public String hi() {
        return "<h2>Good Morning</h2>";
    }

    @GetMapping("/")
    public String abc() {
        return "<h2>Welcome to Spring Boot</h2>";
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployees(@PathVariable int id) {

        Optional<Employee> employee = empRepo.findById(id);

        if(employee.isPresent())
            return ResponseEntity.ok(employee.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employees")
    public Iterable<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    @PostMapping("/employees")
    public String addEmployee(@RequestBody Employee emp) {

        if(empRepo.existsById(emp.getEid()))
            return "Employee ID already exists";

        empRepo.save(emp);

        return "Employee saved successfully";
    }

    @PutMapping("/employees/{id}")
    public String updateEmployees(@PathVariable int id, @RequestBody Employee emp) {

        if(id != emp.getEid())
            return "Employee id mismatch";

        if(!empRepo.existsById(id))
            return "Employee not found";

        empRepo.save(emp);

        return "Employee updated successfully";
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {

        if(!empRepo.existsById(id))
            return "Employee not found";

        empRepo.deleteById(id);

        return "Employee deleted successfully";
    }

    @GetMapping("/employees/role")
    public List<Employee> getEmployeesByDesignation(@RequestParam String desig) {
        return empRepo.findByDesignationIgnoreCase(desig);
    }

    @GetMapping("/employees/salary")
    public List<Employee> getEmployeesBySalary(@RequestParam double salary) {
        return empRepo.findBySalaryLessThan(salary);
    }

    @GetMapping("/employees/age")
    public List<Employee> getEmployeesByAge(@RequestParam int age) {
        return empRepo.findByAgeGreaterThan(age);
    }

    @GetMapping("/employees/custom")
    public List<Employee> getEmployeesByDesignationSortedBySalary(@RequestParam("role") String desig) {
        return empRepo.myCustomQuery(desig);
    }
}