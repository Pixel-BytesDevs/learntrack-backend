package org.tesis.modulodiagnostico.dtos.response;

import java.math.BigDecimal;

public class ScoreAndTopicResponse {
    private BigDecimal score;
    private String nameTopic;

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getNameTopic() {
        return nameTopic;
    }

    public void setNameTopic(String nameTopic) {
        this.nameTopic = nameTopic;
    }
}
