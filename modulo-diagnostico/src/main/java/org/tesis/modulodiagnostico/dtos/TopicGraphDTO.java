package org.tesis.modulodiagnostico.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopicGraphDTO {
    @JsonProperty("temaId")
    private String temaId;
    private String nombre;
    @JsonProperty("gradoRecomendado")
    private Integer gradoRecomendado;

    @JsonProperty("nivelDificultad")
    private String nivelDificultad;
    @JsonProperty("temasRequeridos")
    private List<TopicGraphDTO> temasRequeridos;

    public TopicGraphDTO() {}

    public String getTemaId() {
        return temaId;
    }

    public void setTemaId(String temaId) {
        this.temaId = temaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getGradoRecomendado() {
        return gradoRecomendado;
    }

    public void setGradoRecomendado(Integer gradoRecomendado) {
        this.gradoRecomendado = gradoRecomendado;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public List<TopicGraphDTO> getTemasRequeridos() {
        return temasRequeridos;
    }

    public void setTemasRequeridos(List<TopicGraphDTO> temasRequeridos) {
        this.temasRequeridos = temasRequeridos;
    }
}
