package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.repository.EstudianteRepository;
import com.maurocabrera.springedumanager.service.EstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante findById(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
    }
}