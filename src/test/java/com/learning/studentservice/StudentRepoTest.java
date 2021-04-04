package com.learning.studentservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;

@DataJpaTest
public class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void testStudentByName() {
        //given
        Student createdStudent = testEntityManager.persistAndFlush(new Student(null, "Omid"));
        //when
        Student readStudent = studentRepo.getStudentByName("Omid");
        //then
        BDDAssertions.then(createdStudent.getId()).isNotNull();
        BDDAssertions.then(readStudent.getName()).isEqualTo(createdStudent.getName());
    }

    @Test
    void testGetAvgForActiveStudents() {
        //given
        Student stu1 = Student.builder().name("Omid").active(true).grade(80).build();
        Student stu2 = Student.builder().name("Mike").active(true).grade(90).build();
        Student stu3 = Student.builder().name("Whatever").active(false).grade(100).build();
        Arrays.asList(stu1,stu2,stu3).forEach(testEntityManager::persistFlushFind);
        //when
        Double avg = studentRepo.getAvgGradeForActiveStudents();
        //then
        BDDAssertions.then(avg).isEqualTo(85);

    }

}
