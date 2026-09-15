package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Estudiante;

import java.util.List;

public interface EstudianteService {
    List<Estudiante> findAll();
    Estudiante findById(Long id);
}
