package com.maurocabrera.springedumanager.controller.mvc;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoServiceImpl cursoService;

    @Test
    @WithMockUser(username = "test@email.com", roles = "ESTUDIANTE")
    void whenDashboard_thenReturnsView() throws Exception {
        when(cursoService.findAll()).thenReturn(List.of(new Curso()));

        mockMvc.perform(get("/estudiante/dashboard"))
            .andExpect(status().isOk())
            .andExpect(view().name("student/dashboard"))
            .andExpect(model().attributeExists("cursos"));
    }

    @Test
    @WithMockUser(username = "test@email.com", roles = "ESTUDIANTE")
    void whenCourses_thenReturnsView() throws Exception {
        when(cursoService.findAll()).thenReturn(List.of(new Curso()));

        mockMvc.perform(get("/estudiante/courses"))
            .andExpect(status().isOk())
            .andExpect(view().name("student/courses"))
            .andExpect(model().attributeExists("cursos"));
    }

    @Test
    @WithMockUser(username = "test@email.com", roles = "ESTUDIANTE")
    void whenCourseDetail_thenReturnsView() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Test Course");
        when(cursoService.findById(1L)).thenReturn(curso);

        mockMvc.perform(get("/estudiante/course/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("student/course-detail"))
            .andExpect(model().attributeExists("curso"));
    }
}
