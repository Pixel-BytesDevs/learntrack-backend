package org.tesis.modulodiagnostico.dtos;

public class CuestionarioAlternativaResponse {
    private Long alternativaId;
    private String alternativa;

    public CuestionarioAlternativaResponse() {
    }

    public Long getAlternativaId() {
        return alternativaId;
    }

    public void setAlternativaId(Long alternativaId) {
        this.alternativaId = alternativaId;
    }

    public String getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(String alternativa) {
        this.alternativa = alternativa;
    }
}
