package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> findAll() {
        return cursoRepository.findByActivoTrue();
    }

    public Page<Curso> findAll(Pageable pageable) {
        return cursoRepository.findByActivoTrue(pageable);
    }

    public Curso findById(Long id) {
        return cursoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }
}