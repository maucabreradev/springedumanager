package com.maurocabrera.springedumanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class EvaluacionRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
    private String titulo;
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    @NotNull(message = "El curso es obligatorio")
    private Long cursoId;
    @NotNull(message = "El puntaje máximo es obligatorio")
    @Positive(message = "El puntaje debe ser positivo")
    private BigDecimal puntajeMaximo;
    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;
    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
