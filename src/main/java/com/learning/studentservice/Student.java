package com.learning.studentservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Builder
public class Student {

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id @GeneratedValue
    private Long id;
    private String name;
    private boolean active;
    private int grade;
}
