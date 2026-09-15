package com.maurocabrera.springedumanager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CursoRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    @NotNull(message = "Los créditos son obligatorios")
    private Integer creditos;
    @Size(max = 100, message = "El coordinador no puede exceder 100 caracteres")
    private String coordinador;
    private String fechaInicio;
    private String fechaFin;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public String getCoordinador() { return coordinador; }
    public void setCoordinador(String coordinador) { this.coordinador = coordinador; }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }
}
