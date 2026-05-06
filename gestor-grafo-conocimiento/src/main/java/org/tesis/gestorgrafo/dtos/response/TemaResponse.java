package org.tesis.gestorgrafo.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class TemaResponse {
    private String temaId;
    private String nombre;
    private Integer gradoRecomendado;
    private String nivelDificultad;
    private List<TemaResponse> temasRequeridos;
}
