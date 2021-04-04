package com.learning.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @GetMapping("students/{id}")
    ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student stu = studentService.getStudentById(id);
        if (stu != null)
            return new ResponseEntity<>(stu, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
