package org.tesis.modulodiagnostico.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicRequest {
    @JsonProperty("topicId")
    private String topicId;
    @JsonProperty("topic")
    private String topic;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
