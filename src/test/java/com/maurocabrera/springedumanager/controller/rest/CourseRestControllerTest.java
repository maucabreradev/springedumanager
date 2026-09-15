package com.maurocabrera.springedumanager.controller.rest;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import com.maurocabrera.springedumanager.service.PracticaService;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CursoServiceImpl cursoService;

    @MockBean
    private PracticaService practicaService;

    @MockBean
    private EvaluacionService evaluacionService;

    @Test
    @WithMockUser
    void whenFindAll_thenReturnsCourses() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Test");
        when(cursoService.findAll()).thenReturn(List.of(curso));

        mockMvc.perform(get("/api/courses"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenFindById_thenReturnsCourse() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Test");
        when(cursoService.findById(1L)).thenReturn(curso);

        mockMvc.perform(get("/api/courses/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void whenFindByIdNotFound_thenReturnsNotFound() throws Exception {
        when(cursoService.findById(99L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/courses/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateCourse_thenReturnsCreated() throws Exception {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("New Course");
        when(cursoService.save(any(Curso.class))).thenReturn(curso);

        mockMvc.perform(post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"New Course\",\"descripcion\":\"Desc\",\"creditos\":5}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("New Course"));
    }

    @Test
    void whenDeleteCourse_thenReturnsNoContent() throws Exception {
        doNothing().when(cursoService).deleteById(1L);

        mockMvc.perform(delete("/api/courses/1"))
            .andExpect(status().isNoContent());
    }
}
