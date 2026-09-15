package com.maurocabrera.springedumanager.controller.rest;

import com.maurocabrera.springedumanager.dto.request.EvaluacionRequest;
import com.maurocabrera.springedumanager.dto.response.EvaluacionResponse;
import com.maurocabrera.springedumanager.entity.Evaluacion;
import com.maurocabrera.springedumanager.entity.Practica;
import com.maurocabrera.springedumanager.service.PracticaService;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationRestController {

    private final EvaluacionService evaluacionService;
    private final PracticaService practicaService;

    public EvaluationRestController(EvaluacionService evaluacionService, PracticaService practicaService) {
        this.evaluacionService = evaluacionService;
        this.practicaService = practicaService;
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionResponse>> findAll() {
        List<EvaluacionResponse> evaluations = evaluacionService.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> findById(@PathVariable Long id) {
        try {
            Evaluacion evaluacion = evaluacionService.findById(id);
            return ResponseEntity.ok(toResponse(evaluacion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EvaluacionResponse> create(@RequestBody EvaluacionRequest request) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo(request.getTitulo());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setPuntajeMaximo(request.getPuntajeMaximo());
        evaluacion.setTipo(request.getTipo());
        evaluacion = evaluacionService.save(evaluacion);
        return ResponseEntity.ok(toResponse(evaluacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> update(@PathVariable Long id, @RequestBody EvaluacionRequest request) {
        Evaluacion evaluacion = evaluacionService.findById(id);
        evaluacion.setTitulo(request.getTitulo());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setPuntajeMaximo(request.getPuntajeMaximo());
        evaluacion.setTipo(request.getTipo());
        evaluacion = evaluacionService.save(evaluacion);
        return ResponseEntity.ok(toResponse(evaluacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            evaluacionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<List<EvaluacionResponse>> getResults(@PathVariable Long id) {
        Evaluacion evaluacion = evaluacionService.findById(id);
        List<Practica> practices = practicaService.findByCursoId(evaluacion.getCurso().getId());
        List<EvaluacionResponse> results = practices.stream()
            .map(p -> {
                EvaluacionResponse r = new EvaluacionResponse();
                r.setId(evaluacion.getId());
                r.setTitulo(evaluacion.getTitulo() + " - " + p.getTitulo());
                r.setDescripcion(evaluacion.getDescripcion());
                r.setPuntajeMaximo(evaluacion.getPuntajeMaximo());
                r.setTipo(evaluacion.getTipo());
                r.setCursoId(evaluacion.getCurso().getId());
                return r;
            })
            .collect(Collectors.toList());
        return ResponseEntity.ok(results);
    }

    private EvaluacionResponse toResponse(Evaluacion evaluacion) {
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
