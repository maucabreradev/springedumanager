package com.maurocabrera.springedumanager.repository;

import com.maurocabrera.springedumanager.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByActivoTrue();
    List<Curso> findByCoordinador(String coordinador);
}