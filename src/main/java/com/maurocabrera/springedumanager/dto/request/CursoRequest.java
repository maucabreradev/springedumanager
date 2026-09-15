package com.maurocabrera.springedumanager.dto.request;

public class CursoRequest {
    private String nombre;
    private String descripcion;
    private Integer creditos;
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
