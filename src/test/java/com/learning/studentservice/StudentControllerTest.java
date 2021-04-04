package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @DisplayName("should return the student")
    @Test
    void testStudentControllerById() throws Exception {
        //given
        BDDMockito.given(studentService.getStudentById(ArgumentMatchers.anyLong()))
                .willReturn(Student.builder().id(1L).name("Omid").build());
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Omid"));
    }

    @DisplayName("should throw a student not found exception")
    @Test
    void testStudentNotFoundException() throws Exception {
        //given
        BDDMockito.given(studentService.getStudentById(ArgumentMatchers.anyLong()))
                .willThrow(StudentNotFoundException.class);
        //when//then
        mockMvc.perform(MockMvcRequestBuilders.get("/students/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}