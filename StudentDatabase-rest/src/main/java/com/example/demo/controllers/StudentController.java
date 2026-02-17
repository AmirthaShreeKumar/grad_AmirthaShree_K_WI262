package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entities.Student;
import com.example.demo.repos.StudentRepo;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepo repo;

    // ------------------------------------------------
    // GET /students  → Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // ------------------------------------------------
    // GET /students/{regNo} → Specific student
    @GetMapping("/{regNo}")
    public Optional<Student> getStudent(@PathVariable int regNo) {
        return repo.findById(regNo);
    }

    // ------------------------------------------------
    // POST /students → Insert student
    @PostMapping
    public String addStudent(@RequestBody Student s) {

        if (repo.existsById(s.getRegNo()))
            return "Student already exists";

        repo.save(s);
        return "Student added successfully";
    }

    // ------------------------------------------------
    // PUT /students/{regNo} → Full Update
    @PutMapping("/{regNo}")
    public String updateStudent(@PathVariable int regNo,
                                 @RequestBody Student s) {

        if (s.getRegNo() != regNo)
            return "RegNo mismatch";

        if (!repo.existsById(regNo))
            return "Student not found";

        repo.save(s);
        return "Student updated";
    }

    // ------------------------------------------------
    // PATCH /students/{regNo} → Partial Update
    @PatchMapping("/{regNo}")
    public String patchStudent(@PathVariable int regNo,
                               @RequestBody Student s) {

        Optional<Student> optional = repo.findById(regNo);

        if (!optional.isPresent())
            return "Student not found";

        Student existing = optional.get();

        if (s.getName() != null)
            existing.setName(s.getName());

        if (s.getSchool() != null)
            existing.setSchool(s.getSchool());

        if (s.getStandard() != null)
            existing.setStandard(s.getStandard());

        if (s.getGender() != null)
            existing.setGender(s.getGender());

        if (s.getPercentage() != 0)
            existing.setPercentage(s.getPercentage());

        repo.save(existing);
        return "Student partially updated";
    }

    // ------------------------------------------------
    // DELETE /students/{regNo}
    @DeleteMapping("/{regNo}")
    public String deleteStudent(@PathVariable int regNo) {

        if (!repo.existsById(regNo))
            return "Student not found";

        repo.deleteById(regNo);
        return "Student removed";
    }

    // ------------------------------------------------
    // GET /students/school?name=KV
    @GetMapping("/school")
    public List<Student> studentsBySchool(@RequestParam String name) {
        return repo.findBySchool(name);
    }

    // ------------------------------------------------
    // GET /students/school/count?name=DPS
    @GetMapping("/school/count")
    public long countBySchool(@RequestParam String name) {
        return repo.countBySchool(name);
    }

    // ------------------------------------------------
    // GET /students/school/standard/count?class=5
    @GetMapping("/school/standard/count")
    public long countByStandard(@RequestParam String standard) {
        return repo.countByStandard(standard);
    }

    // ------------------------------------------------
    // GET /students/result?pass=true
    @GetMapping("/result")
    public List<Student> result(@RequestParam boolean pass) {

        if (pass)
            return repo.findByPercentageGreaterThanEqualOrderByPercentageDesc(40);

        else
            return repo.findByPercentageLessThanOrderByPercentageDesc(40);
    }

    // ------------------------------------------------
    // GET /students/strength?gender=MALE&standard=5
    @GetMapping("/strength")
    public long strength(@RequestParam String gender,
                         @RequestParam String standard) {

        return repo.countByGenderAndStandard(gender, standard);
    }
}
