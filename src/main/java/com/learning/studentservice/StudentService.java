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

}
