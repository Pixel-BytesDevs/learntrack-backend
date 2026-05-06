package org.tesis.gestorgrafo.mappers;

import org.springframework.stereotype.Component;
import org.tesis.gestorgrafo.dtos.response.TopicWhithLevelResponse;
import org.tesis.gestorgrafo.dtos.response.TopicWithSimpleResponse;
import org.tesis.gestorgrafo.models.Tema;

@Component
public class TopicMapper {

    public TopicWithSimpleResponse toTopicWithSimpleResponse(Tema tema) {
        TopicWithSimpleResponse topicWithSimpleResponse = new TopicWithSimpleResponse();
        topicWithSimpleResponse.setTopicId(tema.getTemaId());
        topicWithSimpleResponse.setTopic(tema.getNombre());
        return topicWithSimpleResponse;
    }

    public TopicWhithLevelResponse toTopicWithLevelResponse(Tema tema){
        TopicWhithLevelResponse topicWhithLevelResponse = new TopicWhithLevelResponse();
        topicWhithLevelResponse.setTopicId(tema.getTemaId());
        topicWhithLevelResponse.setTopic(tema.getNombre());
        topicWhithLevelResponse.setNivel(tema.getNivel_dificultad());
        return topicWhithLevelResponse;
    }
}
