package com.maurocabrera.springedumanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "practicas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Practica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(length = 50)
    private String estado = "PENDIENTE";
}