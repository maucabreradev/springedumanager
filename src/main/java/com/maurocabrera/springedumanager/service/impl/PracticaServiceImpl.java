package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Practica;
import com.maurocabrera.springedumanager.repository.PracticaRepository;
import com.maurocabrera.springedumanager.service.PracticaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticaServiceImpl implements PracticaService {

    private final PracticaRepository practicaRepository;

    public PracticaServiceImpl(PracticaRepository practicaRepository) {
        this.practicaRepository = practicaRepository;
    }

    @Override
    public List<Practica> findAll() {
        return practicaRepository.findAll();
    }

    @Override
    public Practica findById(Long id) {
        return practicaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Práctica no encontrada con id: " + id));
    }

    @Override
    public Practica save(Practica practica) {
        return practicaRepository.save(practica);
    }

    @Override
    public void deleteById(Long id) {
        practicaRepository.deleteById(id);
    }
}
