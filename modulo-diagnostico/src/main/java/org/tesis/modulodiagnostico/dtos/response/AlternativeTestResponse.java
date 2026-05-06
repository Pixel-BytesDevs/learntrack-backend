package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlternativeTestResponse {
    private Long id;
    @JsonProperty("selected")
    private Boolean selected;
    private String latexExpression;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getLatexExpression() {
        return latexExpression;
    }

    public void setLatexExpression(String latexExpression) {
        this.latexExpression = latexExpression;
    }
}
