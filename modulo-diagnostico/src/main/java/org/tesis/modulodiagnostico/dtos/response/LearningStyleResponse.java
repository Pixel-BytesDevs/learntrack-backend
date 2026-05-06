package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class LearningStyleResponse {
    @JsonProperty("styleName")
    private String styleName;
    private BigDecimal porcentaje;

    public LearningStyleResponse(String styleName, BigDecimal porcentaje) {
        this.styleName = styleName;
        this.porcentaje = porcentaje;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
}
