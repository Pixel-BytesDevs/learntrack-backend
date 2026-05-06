package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tesis.modulodiagnostico.helpers.LatexValidator;

public class PlacementAlternativeResponse {
    private String text;
    private String latex;
    @JsonProperty("isCorrect")
    private Boolean isCorrect;
    private static final Logger log = LoggerFactory.getLogger(PlacementAlternativeResponse.class);

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    // Método que valida y corrige la expresión LaTeX
    public String validateAndFixLatex() {
        //this.setLatex(st);
        /*if (StringUtils.isNotEmpty(this.latex)) {
            // Intentamos validar y corregir la expresión LaTeX
            log.info("Iniciando validacion en alternativa");
            return LatexValidator.validateAndFix(this.latex);
        }*/
        return this.latex; // Si no hay expresión, devolvemos la original (null o vacía)
    }
}
