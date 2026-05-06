package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tesis.modulodiagnostico.helpers.LatexValidator;

import java.util.List;

public class PlacementQuestionResponse {
    private Integer grade;
    private String difficulty;
    @JsonProperty("topicId")
    private String topicId;
    private String sentence;
    @JsonProperty("expressionLatex")
    private String expressionLatex;
    private List<PlacementAlternativeWithIdResponse> alternatives;
    private static final Logger log = LoggerFactory.getLogger(PlacementQuestionResponse.class);
    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getExpressionLatex() {
        return expressionLatex;
    }

    public void setExpressionLatex(String expressionLatex) {
        this.expressionLatex = expressionLatex;
    }

    public List<PlacementAlternativeWithIdResponse> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<PlacementAlternativeWithIdResponse> alternatives) {
        this.alternatives = alternatives;
    }

    // Método que valida y corrige la expresión LaTeX
    public String validateAndFixLatex() {
        //this.setExpressionLatex(st);

        if (StringUtils.isNotEmpty(this.expressionLatex)) {
            // Intentamos validar y corregir la expresión LaTeX
            log.info("Iniciando validación");
            return LatexValidator.validateAndFix(this.expressionLatex);
        }
        return this.expressionLatex; // Si no hay expresión, devolvemos la original (null o vacía)
    }
}
