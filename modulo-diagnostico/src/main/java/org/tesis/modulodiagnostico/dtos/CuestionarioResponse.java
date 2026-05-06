package org.tesis.modulodiagnostico.dtos;

import java.util.List;

public class CuestionarioResponse {
    private List<CuestionarioPreguntaResponse> preguntas;

    public CuestionarioResponse() {
    }

    public List<CuestionarioPreguntaResponse> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<CuestionarioPreguntaResponse> preguntas) {
        this.preguntas = preguntas;
    }
}
