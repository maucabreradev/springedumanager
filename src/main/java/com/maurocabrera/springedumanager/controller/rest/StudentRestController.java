package com.maurocabrera.springedumanager.controller.rest;

import com.maurocabrera.springedumanager.dto.response.MatriculaResponse;
import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.service.MatriculaService;
import com.maurocabrera.springedumanager.service.impl.EstudianteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    private final EstudianteServiceImpl estudianteService;
    private final MatriculaService matriculaService;

    public StudentRestController(EstudianteServiceImpl estudianteService, MatriculaService matriculaService) {
        this.estudianteService = estudianteService;
        this.matriculaService = matriculaService;
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> findAll() {
        return ResponseEntity.ok(estudianteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> findById(@PathVariable Long id) {
        try {
            Estudiante estudiante = estudianteService.findById(id);
            return ResponseEntity.ok(estudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/enroll/{courseId}")
    public ResponseEntity<MatriculaResponse> enroll(@PathVariable Long id, @PathVariable Long courseId) {
        try {
            var matricula = matriculaService.enrollStudent(id, courseId);
            return ResponseEntity.ok(toResponse(matricula));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/enroll/{courseId}")
    public ResponseEntity<Void> unenroll(@PathVariable Long id, @PathVariable Long courseId) {
        try {
            matriculaService.deleteByEstudianteIdAndCursoId(id, courseId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private MatriculaResponse toResponse(com.maurocabrera.springedumanager.entity.Matricula matricula) {
        MatriculaResponse response = new MatriculaResponse();
        response.setId(matricula.getId());
        response.setEstudianteId(matricula.getEstudiante() != null ? matricula.getEstudiante().getId() : null);
        response.setCursoId(matricula.getCurso() != null ? matricula.getCurso().getId() : null);
        response.setEstado(matricula.getEstado());
        response.setFechaMatricula(matricula.getFechaMatricula() != null ? matricula.getFechaMatricula().toString() : null);
        return response;
    }
}
