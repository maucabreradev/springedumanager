package com.maurocabrera.springedumanager.repository;

import com.maurocabrera.springedumanager.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    List<Evaluacion> findByCursoId(Long cursoId);
    List<Evaluacion> findByTipo(String tipo);
    List<Evaluacion> findByCursoIdIn(List<Long> cursoIds);
}