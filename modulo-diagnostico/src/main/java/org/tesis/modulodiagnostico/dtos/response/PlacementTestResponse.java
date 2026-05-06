package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class PlacementTestResponse {
    private Long id;
    private Integer duration;
    private List<QuestionTestResponse> questionTestResponses;

    @JsonProperty("startedAt")
    private LocalDateTime startedAt;

    @JsonProperty("userId")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<QuestionTestResponse> getQuestionTestResponses() {
        return questionTestResponses;
    }

    public void setQuestionTestResponses(List<QuestionTestResponse> questionTestResponses) {
        this.questionTestResponses = questionTestResponses;
    }
}
