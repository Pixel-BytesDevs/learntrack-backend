package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.request.TopicRequest;
import org.tesis.modulodiagnostico.models.Topic;

@Component
public class TopicMapper {

    public Topic toTopicEntity(TopicRequest topicRequest) {
        Topic topic = new Topic();
        topic.setId(topicRequest.getTopicId());
        topic.setName(topicRequest.getTopic());
        return topic;
    }

    public TopicRequest toTopicRequest(Topic topic) {
        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setTopicId(topic.getId());
        topicRequest.setTopic(topic.getName());
        return topicRequest;
    }

}
