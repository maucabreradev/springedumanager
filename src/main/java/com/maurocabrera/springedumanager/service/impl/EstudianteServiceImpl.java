package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteServiceImpl {

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Estudiante findById(Long id) {
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + id));
    }

    public Estudiante findByEmail(String email) {
        return estudianteRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con email: " + email));
    }
}