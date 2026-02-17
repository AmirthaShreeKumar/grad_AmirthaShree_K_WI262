package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Student; 
import com.example.demo.service.StudentService;

import org.springframework.ui.Model;


@Controller
public class StudentController {

    @Autowired
    StudentService service;

    @GetMapping("/")
    public String showForm(Model model){
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Student student){
        service.saveStudent(student);
        return "success";
    }
}
