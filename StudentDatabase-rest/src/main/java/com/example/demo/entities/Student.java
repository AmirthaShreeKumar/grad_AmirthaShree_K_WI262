package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @Column(name = "REG_NO")
    private int regNo;

    @Column(name = "ROLL_NO")
    private int rollNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STANDARD")
    private String standard;

    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PERCENTAGE")
    private double percentage;

    // Getters and Setters

    public int getRegNo() {
        return regNo;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
