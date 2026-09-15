package com.maurocabrera.springedumanager.controller.rest;

import com.maurocabrera.springedumanager.dto.request.CursoRequest;
import com.maurocabrera.springedumanager.dto.response.CursoResponse;
import com.maurocabrera.springedumanager.dto.response.EvaluacionResponse;
import com.maurocabrera.springedumanager.dto.response.PracticaResponse;
import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.service.PracticaService;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import com.maurocabrera.springedumanager.service.impl.CursoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

    private final CursoServiceImpl cursoService;
    private final PracticaService practicaService;
    private final EvaluacionService evaluacionService;

    public CourseRestController(CursoServiceImpl cursoService, PracticaService practicaService, EvaluacionService evaluacionService) {
        this.cursoService = cursoService;
        this.practicaService = practicaService;
        this.evaluacionService = evaluacionService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        List<Curso> cursos = cursoService.findAll();
        List<CursoResponse> response = cursos.stream().map(this::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
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

    @PostMapping
    public ResponseEntity<CursoResponse> create(@RequestBody CursoRequest request) {
        Curso curso = new Curso();
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setCreditos(request.getCreditos());
        curso.setCoordinador(request.getCoordinador());
        curso = cursoService.save(curso);
        return ResponseEntity.ok(toResponse(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> update(@PathVariable Long id, @RequestBody CursoRequest request) {
        Curso curso = cursoService.findById(id);
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setCreditos(request.getCreditos());
        curso.setCoordinador(request.getCoordinador());
        curso = cursoService.save(curso);
        return ResponseEntity.ok(toResponse(curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cursoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/practices")
    public ResponseEntity<List<PracticaResponse>> getPractices(@PathVariable Long id) {
        List<PracticaResponse> practices = cursoService.findById(id).getPracticas().stream()
            .map(this::toPracticaResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(practices);
    }

    @GetMapping("/{id}/evaluations")
    public ResponseEntity<List<EvaluacionResponse>> getEvaluations(@PathVariable Long id) {
        List<EvaluacionResponse> evaluations = cursoService.findById(id).getEvaluaciones().stream()
            .map(this::toEvaluacionResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(evaluations);
    }

    private CursoResponse toResponse(Curso curso) {
        CursoResponse response = new CursoResponse();
        response.setId(curso.getId());
        response.setNombre(curso.getNombre());
        response.setDescripcion(curso.getDescripcion());
        response.setCreditos(curso.getCreditos());
        return response;
    }

    private PracticaResponse toPracticaResponse(com.maurocabrera.springedumanager.entity.Practica practica) {
        PracticaResponse response = new PracticaResponse();
        response.setId(practica.getId());
        response.setTitulo(practica.getTitulo());
        response.setDescripcion(practica.getDescripcion());
        response.setEstado(practica.getEstado());
        response.setFechaEntrega(practica.getFechaEntrega() != null ? practica.getFechaEntrega().toString() : null);
        response.setCursoId(practica.getCurso() != null ? practica.getCurso().getId() : null);
        return response;
    }

    private EvaluacionResponse toEvaluacionResponse(com.maurocabrera.springedumanager.entity.Evaluacion evaluacion) {
        EvaluacionResponse response = new EvaluacionResponse();
        response.setId(evaluacion.getId());
        response.setTitulo(evaluacion.getTitulo());
        response.setDescripcion(evaluacion.getDescripcion());
        response.setPuntajeMaximo(evaluacion.getPuntajeMaximo());
        response.setFecha(evaluacion.getFecha() != null ? evaluacion.getFecha().toString() : null);
        response.setTipo(evaluacion.getTipo());
        response.setCursoId(evaluacion.getCurso() != null ? evaluacion.getCurso().getId() : null);
        return response;
    }
}
