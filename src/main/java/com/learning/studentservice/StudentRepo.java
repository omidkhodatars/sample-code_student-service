package com.learning.studentservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Student getStudentByName(String student);

    @Query("select avg (grade) from Student where active=true")
    Double getAvgGradeForActiveStudents();
}
