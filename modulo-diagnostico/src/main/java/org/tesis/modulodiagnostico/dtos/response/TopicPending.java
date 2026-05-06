package org.tesis.modulodiagnostico.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TopicPending {
    @JsonProperty("topicId")
    private String topicId;
    @JsonProperty("topicName")
    private String topicName;
    @JsonProperty("domainLevel")
    private BigDecimal domainLevel;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public BigDecimal getDomainLevel() {
        return domainLevel;
    }

    public void setDomainLevel(BigDecimal domainLevel) {
        this.domainLevel = domainLevel;
    }
}
