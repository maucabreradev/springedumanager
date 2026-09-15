package com.maurocabrera.springedumanager.dto.response;

public class MatriculaResponse {
    private Long id;
    private Long estudianteId;
    private Long cursoId;
    private String estado;
    private String fechaMatricula;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(String fechaMatricula) { this.fechaMatricula = fechaMatricula; }
}
