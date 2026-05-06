package org.tesis.modulodiagnostico.dtos;

import java.util.List;

public class CuestionarioPreguntaResponse {
    private Long preguntaId;
    private String sentencia;
    private List<CuestionarioAlternativaResponse> alternativas;

    public CuestionarioPreguntaResponse() {
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getSentencia() {
        return sentencia;
    }

    public void setSentencia(String sentencia) {
        this.sentencia = sentencia;
    }

    public List<CuestionarioAlternativaResponse> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<CuestionarioAlternativaResponse> alternativas) {
        this.alternativas = alternativas;
    }
}
