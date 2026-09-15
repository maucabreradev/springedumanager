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
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoServiceImpl cursoService;

    @Test
    @WithMockUser(roles = "COORDINADOR")
    void whenListCourses_thenReturnsView() throws Exception {
        when(cursoService.findAll()).thenReturn(List.of(new Curso()));

        mockMvc.perform(get("/admin/courses"))
            .andExpect(status().isOk())
            .andExpect(view().name("course/list"))
            .andExpect(model().attributeExists("cursos"));

        verify(cursoService, times(1)).findAll();
    }

    @Test
    @WithMockUser(roles = "COORDINADOR")
    void whenCreateCourse_thenRedirects() throws Exception {
        mockMvc.perform(post("/admin/courses")
                .with(csrf())
                .param("nombre", "Test Course")
                .param("descripcion", "Test Desc")
                .param("creditos", "5"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/courses"));

        verify(cursoService, times(1)).save(any(Curso.class));
    }

    @Test
    @WithMockUser(roles = "COORDINADOR")
    void whenDeleteCourse_thenRedirects() throws Exception {
        doNothing().when(cursoService).deleteById(1L);

        mockMvc.perform(get("/admin/courses/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/courses"));

        verify(cursoService, times(1)).deleteById(1L);
    }
}
