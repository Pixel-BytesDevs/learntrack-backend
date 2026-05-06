package org.tesis.modulodiagnostico.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EvaluateRecommendationRequest {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("topicId")
    private String topicId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }
}
