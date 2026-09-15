package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Evaluacion;

import java.util.List;

public interface EvaluacionService {
    List<Evaluacion> findAll();
    Evaluacion findById(Long id);
    Evaluacion save(Evaluacion evaluacion);
    void deleteById(Long id);
    List<Evaluacion> findByCursoIdIn(List<Long> cursoIds);
}
