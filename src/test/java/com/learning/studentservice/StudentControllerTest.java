package com.learning.studentservice;

import com.learning.studentservice.exception.StudentNotFoundException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @DisplayName("calling rest api with student ID should return the student")
    @Test
    void testStudentControllerById() throws Exception {
        //given
        BDDMockito.given(studentService.getStudentById(ArgumentMatchers.anyLong()))
                .willReturn(Student.builder().id(1L).name("Omid").build());
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
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
        mockMvc.perform(MockMvcRequestBuilders.get("/student/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @DisplayName("calling post method should create a student")
    @Test
    void rest_createStudent_returnStudent() throws Exception {
        //given
        BDDMockito.given(studentService.createStudent(ArgumentMatchers.any())).
                willReturn(Student.builder().id(1L).name("Omid").build());
        JSONObject jsonObject = new JSONObject().put("name", "Whatever");
        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Omid"));
    }
}