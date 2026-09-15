package com.maurocabrera.springedumanager.controller.rest;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.dto.response.CursoResponse;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CursoServiceImpl cursoService;

    public CourseRestController(CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<CursoResponse> cursos = cursoService.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> findById(@PathVariable Long id) {
        try {
            Curso curso = cursoService.findById(id);
            return ResponseEntity.ok(toResponse(curso));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private CursoResponse toResponse(Curso curso) {
        CursoResponse response = new CursoResponse();
        response.setId(curso.getId());
        response.setNombre(curso.getNombre());
        response.setDescripcion(curso.getDescripcion());
        response.setCreditos(curso.getCreditos());
        return response;
    }
}