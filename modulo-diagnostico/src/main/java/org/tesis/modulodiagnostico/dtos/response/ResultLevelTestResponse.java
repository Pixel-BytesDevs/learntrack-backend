package org.tesis.modulodiagnostico.dtos.response;

import java.math.BigDecimal;
import java.util.List;

public class ResultLevelTestResponse {
    List<ScoreAndTopicResponse> scores;
    String nameFirsTopic;
    public List<ScoreAndTopicResponse> getScores() {
        return scores;
    }

    public void setScores(List<ScoreAndTopicResponse> scores) {
        this.scores = scores;
    }

    public String getNameFirsTopic() {
        return nameFirsTopic;
    }

    public void setNameFirsTopic(String nameFirsTopic) {
        this.nameFirsTopic = nameFirsTopic;
    }
}
