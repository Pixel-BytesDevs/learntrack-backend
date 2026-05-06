package org.tesis.modulodiagnostico.dtos.extras;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRecommendation {

    private String message;
    private String tema;
    private Boolean success;
    private RecommendationData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTema() {
        if (tema != null && !tema.isBlank()) {
            return tema;
        }
        if (data != null && data.getTopic() != null) {
            return data.getTopic().getTopicName();
        }
        return null;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public RecommendationData getData() {
        return data;
    }

    public void setData(RecommendationData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecommendationData {
        private RecommendationTopic topic;

        public RecommendationTopic getTopic() {
            return topic;
        }

        public void setTopic(RecommendationTopic topic) {
            this.topic = topic;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecommendationTopic {
        private String topicId;
        private String topicName;
        private Double domainLevel;

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

        public Double getDomainLevel() {
            return domainLevel;
        }

        public void setDomainLevel(Double domainLevel) {
            this.domainLevel = domainLevel;
        }
    }
}
