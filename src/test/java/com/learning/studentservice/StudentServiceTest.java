package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class StudentServiceTest {

    @MockBean
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @DisplayName("Returning saved students from service layer")
    @Test
    void getStudentByIdTest() {
        //given
        Student student = new Student(1234L, "TestUser");
        BDDMockito.given(studentRepo.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(student));
        //when
        Student readStu = studentService.getStudentById(student.getId());
        //then
        BDDAssertions.then(readStu.getId()).isNotNull();
        BDDAssertions.then(readStu.getName()).isEqualTo("TestUser");
        BDDAssertions.then(readStu.getId()).isEqualTo(student.getId());

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
        Student newStudent = new Student(1234L, "Omid");
        Student negativeTest = new Student(4321L, "Whatever");
        BDDMockito.given(studentRepo.save(ArgumentMatchers.any())).willReturn(newStudent);

        //when
        Student createdStudent = studentService.createStudent(newStudent);

        //then
        BDDAssertions.then(createdStudent).isEqualTo(newStudent);
        BDDAssertions.then(createdStudent).isNotEqualTo(negativeTest);
    }

    @DisplayName("service: should update an existing student")
    @Test
    void updateStudent() {
        //given
        Student existingStudent = new Student(1234L, "Test");
        Student newStudent = new Student(1234L, "Omid");
        BDDMockito.given(studentRepo.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(existingStudent));
        BDDMockito.given(studentRepo.save(ArgumentMatchers.any())).willReturn(newStudent);
        //when
        Student updatedStudent = studentService.updateStudent(newStudent);
        //then
        BDDAssertions.then(updatedStudent).isEqualTo(newStudent);
        BDDAssertions.then(updatedStudent.getId()).isEqualTo(newStudent.getId()).isEqualTo(existingStudent.getId());
        BDDAssertions.then(updatedStudent.getName()).isEqualTo("Omid").isEqualTo(newStudent.getName());
    }
}
