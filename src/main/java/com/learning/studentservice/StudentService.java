package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public Student getStudentById(Long id) {
        return studentRepo.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student createStudent(Student newStudent) {
        return studentRepo.save(newStudent);
    }

    public Student updateStudent(Student updatingStudent) {
        Student existingStudent = studentRepo.findById(updatingStudent.getId()).orElseThrow(StudentNotFoundException::new);
        existingStudent.setName(updatingStudent.getName());
        return studentRepo.save(existingStudent);
    }

}
