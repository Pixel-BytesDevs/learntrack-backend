package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class ProgressGraph {
    private String id;
    private String label;
    private BigDecimal dominio;
    @JsonProperty("isActive")
    private Boolean isActive;
    private List<String> conexiones;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getDominio() {
        return dominio;
    }

    public void setDominio(BigDecimal dominio) {
        this.dominio = dominio;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<String> getConexiones() {
        return conexiones;
    }

    public void setConexiones(List<String> conexiones) {
        this.conexiones = conexiones;
    }
}
