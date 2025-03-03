package com.example.revisionSpringBoot.controller;

import com.example.revisionSpringBoot.bean.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @GetMapping("get-student")
    public Student getStudent() {
        Student student = new Student(101, "Arup", "Mathura");
        return student;
    }

    @GetMapping("get-students")
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(102, "aaa", "aaaaa"));
        students.add(new Student(103, "bbb", "bbbbb"));
        students.add(new Student(104, "ccc", "ccccc"));
        students.add(new Student(105, "ddd", "ddddd"));
        return students;
    }

    // {id} --> URI template variable
    @GetMapping("students/{id}/{first-name}/{last-name}")
    public Student studentPathVariable(@PathVariable("id") int studentId,
                                       @PathVariable("first-name") String firstName,
                                       @PathVariable("last-name") String lastName) {
        return new Student(studentId, firstName, lastName);
    }

    // localhost:8080/students/query?id=111&firstName=arup&lastName=mathura
    @GetMapping("/students/query")
    public Student studentQueryParameter(@RequestParam int id,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName) {
        return new Student(id, firstName, lastName);
    }

    @PostMapping("/students/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        System.out.println(student.getId());
        System.out.println(student.getFirstName());
        System.out.println(student.getLastName());
        return student;
    }
}
