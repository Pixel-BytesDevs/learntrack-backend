package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuestionTestResponse {
    private Long id;
    private Integer timeTakenInSeconds;
    private Integer attempts;
    private Integer hintsUsed;
    private Boolean isCorrect;
    private Double gradePenalized;
    private String statement;
    private DifficultyResponse difficulty;
    @JsonProperty("topicId")
    private Long topicId;
    private List<AlternativeTestResponse> alternatives;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimeTakenInSeconds() {
        return timeTakenInSeconds;
    }

    public void setTimeTakenInSeconds(Integer timeTakenInSeconds) {
        this.timeTakenInSeconds = timeTakenInSeconds;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(Integer hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public Double getGradePenalized() {
        return gradePenalized;
    }

    public void setGradePenalized(Double gradePenalized) {
        this.gradePenalized = gradePenalized;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public DifficultyResponse getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyResponse difficulty) {
        this.difficulty = difficulty;
    }

    public List<AlternativeTestResponse> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<AlternativeTestResponse> alternatives) {
        this.alternatives = alternatives;
    }
}
