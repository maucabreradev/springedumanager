package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.repository.CursoRepository;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    @Test
    void whenFindAll_thenReturnsActiveCourses() {
        Curso curso = new Curso();
        curso.setId(1L);
        curso.setNombre("Math 101");
        when(cursoRepository.findByActivoTrue()).thenReturn(List.of(curso));

        List<Curso> result = cursoService.findAll();

        assertEquals(1, result.size());
        assertEquals("Math 101", result.get(0).getNombre());
        verify(cursoRepository, times(1)).findByActivoTrue();
    }

    @Test
    void whenFindById_thenReturnCourse() {
        Curso curso = new Curso();
        curso.setId(1L);
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso result = cursoService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void whenSave_thenReturnSavedCourse() {
        Curso curso = new Curso();
        curso.setNombre("Test");
        when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Curso result = cursoService.save(curso);

        assertNotNull(result);
        verify(cursoRepository, times(1)).save(curso);
    }

    @Test
    void whenDeleteById_thenCallsDelete() {
        cursoService.deleteById(1L);
        verify(cursoRepository, times(1)).deleteById(1L);
    }
}
