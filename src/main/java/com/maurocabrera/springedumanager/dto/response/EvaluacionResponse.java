package com.maurocabrera.springedumanager.dto.response;

import java.math.BigDecimal;

public class EvaluacionResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private BigDecimal puntajeMaximo;
    private String fecha;
    private String tipo;
    private Long cursoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
}
