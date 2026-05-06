package org.tesis.gestorgrafo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NodoDTO {
    private String id;
    private String nombre;

    @JsonProperty("grado_recomendado")
    private Integer gradoRecomendado;

    @JsonProperty("nivel_dificultad")
    private String nivelDificultad;

    @JsonProperty("desempeños_asociados")
    private String[] desempeniosAsoacidos;

    private String[] prerrequisitos;
}
