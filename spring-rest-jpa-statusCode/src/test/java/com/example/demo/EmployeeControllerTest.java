package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.EmpController;
import com.example.demo.entities.Employee;
import com.example.demo.repo.EmpRepo;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    EmpRepo empRepo;

    @InjectMocks
    EmpController controller;

    @Test
    void testGetEmployeeFound() {

        Employee emp = new Employee();
        emp.setEid(1);
        emp.setName("John");

        when(empRepo.findById(1)).thenReturn(Optional.of(emp));

        Employee result = (Employee) controller.getEmployees(1).getBody();

        assertEquals("John", result.getName());
    }
}