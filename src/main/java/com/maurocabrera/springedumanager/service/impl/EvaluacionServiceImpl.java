package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Evaluacion;
import com.maurocabrera.springedumanager.repository.EvaluacionRepository;
import com.maurocabrera.springedumanager.service.EvaluacionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;

    public EvaluacionServiceImpl(EvaluacionRepository evaluacionRepository) {
        this.evaluacionRepository = evaluacionRepository;
    }

    @Override
    public List<Evaluacion> findAll() {
        return evaluacionRepository.findAll();
    }

    @Override
    public Evaluacion findById(Long id) {
        return evaluacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con id: " + id));
    }

    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        return evaluacionRepository.save(evaluacion);
    }

    @Override
    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }
}
