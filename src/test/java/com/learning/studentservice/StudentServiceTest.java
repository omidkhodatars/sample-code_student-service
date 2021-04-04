package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class StudentServiceTest {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @DisplayName("Returning saved students from service layer")
    @Test
    void getStudentByIdTest() {
        //given
        Student savedStu = studentRepo.save(new Student(null, "test"));
        //when
        Student readStu = studentService.getStudentById(savedStu.getId());
        //then
        BDDAssertions.then(readStu.getId()).isNotNull();
        BDDAssertions.then(readStu.getName()).isEqualTo("test");
        BDDAssertions.then(readStu.getId()).isEqualTo(savedStu.getId());

    }

    @DisplayName("throw an exception when a student cannot be found")
    @Test
    void throwNotFoundException() {
        //given an Id which does not exist
        Long studentId = 1234L;
        //when
        Throwable exception = Assertions.catchThrowable(() -> studentService.getStudentById(studentId));
        //then
        BDDAssertions.then(exception).isInstanceOf(StudentNotFoundException.class);
    }
}
