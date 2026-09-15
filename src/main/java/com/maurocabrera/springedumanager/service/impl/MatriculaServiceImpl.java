package com.maurocabrera.springedumanager.service.impl;

import com.maurocabrera.springedumanager.entity.Curso;
import com.maurocabrera.springedumanager.entity.Estudiante;
import com.maurocabrera.springedumanager.entity.Matricula;
import com.maurocabrera.springedumanager.repository.CursoRepository;
import com.maurocabrera.springedumanager.repository.EstudianteRepository;
import com.maurocabrera.springedumanager.repository.MatriculaRepository;
import com.maurocabrera.springedumanager.service.MatriculaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public MatriculaServiceImpl(MatriculaRepository matriculaRepository, EstudianteRepository estudianteRepository, CursoRepository cursoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    @Transactional
    public Matricula enrollStudent(Long estudianteId, Long cursoId) {
        if (isEnrolled(estudianteId, cursoId)) {
            throw new RuntimeException("El estudiante ya está matriculado en este curso");
        }
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id: " + estudianteId));
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + cursoId));
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setCurso(curso);
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setEstado("ACTIVA");
        return matriculaRepository.save(matricula);
    }

    @Override
    public List<Matricula> findByEstudianteId(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }

    @Override
    public List<Matricula> findByCursoId(Long cursoId) {
        return matriculaRepository.findByCursoId(cursoId);
    }

    @Override
    public boolean isEnrolled(Long estudianteId, Long cursoId) {
        return matriculaRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId).isPresent();
    }
}
