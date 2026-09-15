package com.maurocabrera.springedumanager.service;

import com.maurocabrera.springedumanager.entity.Practica;

import java.util.List;

public interface PracticaService {
    List<Practica> findAll();
    Practica findById(Long id);
    Practica save(Practica practica);
    void deleteById(Long id);
    List<Practica> findByCursoIdIn(List<Long> cursoIds);
    List<Practica> findByCursoId(Long cursoId);
}
