package org.tesis.modulodiagnostico.dtos;

import java.util.List;

public class GraphDTO {
    private Integer id;
    private String description;
    private String nivel;
    private String area;
    private String curriculoRef;
    private List<TopicGraphDTO> temas;

    public GraphDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TopicGraphDTO> getTemas() {
        return temas;
    }

    public void setTemas(List<TopicGraphDTO> temas) {
        this.temas = temas;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
