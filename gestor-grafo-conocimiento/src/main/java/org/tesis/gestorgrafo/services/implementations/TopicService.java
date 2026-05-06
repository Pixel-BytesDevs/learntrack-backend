package org.tesis.gestorgrafo.services.implementations;

import org.springframework.stereotype.Service;
import org.tesis.gestorgrafo.dtos.response.TopicWhithLevelResponse;
import org.tesis.gestorgrafo.dtos.response.TopicWithSimpleResponse;
import org.tesis.gestorgrafo.mappers.TopicMapper;
import org.tesis.gestorgrafo.models.Tema;
import org.tesis.gestorgrafo.repositories.TemaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TemaRepository temaRepository;
    private final TopicMapper topicMapper;

    public TopicService(TemaRepository temaRepository, TopicMapper topicMapper) {
        this.temaRepository = temaRepository;
        this.topicMapper = topicMapper;
    }

    public List<TopicWithSimpleResponse> getTopics() {
        List<Tema> temas = temaRepository.findAll();
        return  temas.stream().map(topicMapper::toTopicWithSimpleResponse).collect(Collectors.toList());
    }

    public List<TopicWithSimpleResponse> getPrerrequisitesByTopicId(String id){
        Tema tema = temaRepository.findByTemaId(id);
        return tema.getTemasRequeridos().stream().map(topicMapper::toTopicWithSimpleResponse).toList();
    }

    public List<TopicWhithLevelResponse> getPrerrequisitesWithLevelByTopicId(String id){
        Tema tema = temaRepository.findByTemaId(id);
        return tema.getTemasRequeridos().stream().map(topicMapper::toTopicWithLevelResponse).toList();
    }

    public List<TopicWithSimpleResponse> getPostrequisitesByTopicId(String id){
        List<Tema> temas = temaRepository.findAll();
        Tema tema = temaRepository.findByTemaId(id);
        List<Tema> postRequisites = new ArrayList<>();
        temas.forEach(tem -> {
            System.out.println("Id de tema: "+tem.getTemaId() + ", Temas requeridos: "+tem.getTemasRequeridos().size());
            System.out.println("Tema: "+tema.getTemaId());
            /*if(tem.getTemasRequeridos().contains(tema)){
                postRequisites.add(tem);
            }*/
            tem.getTemasRequeridos().forEach(r -> {
                if(r.getTemaId().equals(tema.getTemaId()))
                {
                    postRequisites.add(tem);
                }
            });
        });
        return postRequisites.stream().map(topicMapper::toTopicWithSimpleResponse).toList();
    }

}
