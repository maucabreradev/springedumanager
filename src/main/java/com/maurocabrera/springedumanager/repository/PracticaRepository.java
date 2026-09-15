package com.maurocabrera.springedumanager.repository;

import com.maurocabrera.springedumanager.entity.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticaRepository extends JpaRepository<Practica, Long> {
    List<Practica> findByCursoId(Long cursoId);
    List<Practica> findByEstado(String estado);
}