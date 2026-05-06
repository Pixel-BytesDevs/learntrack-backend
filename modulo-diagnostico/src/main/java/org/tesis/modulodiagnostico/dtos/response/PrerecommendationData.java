package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PrerecommendationData {

    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("learningStyleResponses")
    private List<LearningStyleResponse> learningStyleResponses;
    @JsonProperty("topicPendings")
    private List<TopicPending> topicPendings;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<LearningStyleResponse> getLearningStyleResponses() {
        return learningStyleResponses;
    }

    public void setLearningStyleResponses(List<LearningStyleResponse> learningStyleResponses) {
        this.learningStyleResponses = learningStyleResponses;
    }

    public List<TopicPending> getTopicPendings() {
        return topicPendings;
    }

    public void setTopicPendings(List<TopicPending> topicPendings) {
        this.topicPendings = topicPendings;
    }
}
