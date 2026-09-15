package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Matricula;

import java.util.List;

public interface MatriculaService {
    Matricula enrollStudent(Long estudianteId, Long cursoId);
    List<Matricula> findByEstudianteId(Long estudianteId);
    List<Matricula> findByCursoId(Long cursoId);
    boolean isEnrolled(Long estudianteId, Long cursoId);
}
