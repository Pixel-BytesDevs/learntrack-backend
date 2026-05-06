package org.tesis.gestorgrafo.dtos.response;

import lombok.Data;

import java.util.List;


public class CompetenciaResponse {

    private Long id;
    private String descripcion;
    private String nivel;
    private String area;
    private String curriculoRef;
    private List<TemaResponse> temas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCurriculoRef() {
        return curriculoRef;
    }

    public void setCurriculoRef(String curriculoRef) {
        this.curriculoRef = curriculoRef;
    }

    public List<TemaResponse> getTemas() {
        return temas;
    }

    public void setTemas(List<TemaResponse> temas) {
        this.temas = temas;
    }
}
