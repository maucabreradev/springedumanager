package com.maurocabrera.springedumanager.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas")
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "fecha_matricula")
    private LocalDateTime fechaMatricula;

    private String estado = "ACTIVA";

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
    public LocalDateTime getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDateTime fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}