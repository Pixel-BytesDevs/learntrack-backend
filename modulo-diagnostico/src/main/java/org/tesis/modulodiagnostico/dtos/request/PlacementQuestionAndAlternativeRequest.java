package org.tesis.modulodiagnostico.dtos.request;

import org.tesis.modulodiagnostico.models.Topic;

import java.util.List;

public class PlacementQuestionAndAlternativeRequest {
    private Integer grade;
    private TopicRequest topic;

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public TopicRequest getTopic() {
        return topic;
    }

    public void setTopic(TopicRequest topic) {
        this.topic = topic;
    }
}
