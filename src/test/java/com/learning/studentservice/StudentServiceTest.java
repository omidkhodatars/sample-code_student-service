package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @DisplayName("service: should create a new student")
    @Test
    void createStudent() {
        //given
        Student newStudent = new Student(null, "Omid");
        //when
        Student createdStudent = studentService.createStudent(newStudent);
        Optional<Student> studentFromRepo = studentRepo.findById(createdStudent.getId());
        //then
        BDDAssertions.then(studentFromRepo.isPresent()).isTrue();
    }

    @DisplayName("service: should update an existing student")
    @Test
    void updateStudent() {
        //given
        Student existingStudent = studentRepo.save(new Student(null, "Test"));
        Student updatingStudent = new Student(existingStudent.getId(), "Omid");
        //when
        Student updatedStudent = studentService.updateStudent(updatingStudent);
        Optional<Student> updatedFromRepo = studentRepo.findById(existingStudent.getId());
        //then
        BDDAssertions.then(updatedFromRepo.isPresent()).isTrue();
        BDDAssertions.then(updatedFromRepo.get().getId()).isEqualTo(updatedStudent.getId()).isEqualTo(existingStudent.getId());
        BDDAssertions.then(updatedFromRepo.get().getName()).isEqualTo("Omid").isEqualTo(updatedStudent.getName());
    }
}
