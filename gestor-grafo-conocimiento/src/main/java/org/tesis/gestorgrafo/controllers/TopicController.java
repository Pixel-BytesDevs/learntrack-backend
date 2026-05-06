package org.tesis.gestorgrafo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesis.gestorgrafo.dtos.response.TopicWhithLevelResponse;
import org.tesis.gestorgrafo.dtos.response.TopicWithSimpleResponse;
import org.tesis.gestorgrafo.models.Tema;
import org.tesis.gestorgrafo.services.implementations.TopicService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicWithSimpleResponse>> getAllTopics() {
        List<TopicWithSimpleResponse> topics = topicService.getTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/required/{id}")
    public ResponseEntity<List<TopicWithSimpleResponse>> getPrerrequisitesByTopicId(@PathVariable("id") String id){
        List<TopicWithSimpleResponse> prerrequisites = topicService.getPrerrequisitesByTopicId(id);
        return ResponseEntity.ok(prerrequisites);
    }
    
    @GetMapping("/post-required/{id}")
    public ResponseEntity<List<TopicWithSimpleResponse>> getPostrequisitesByTopicId(@PathVariable("id") String id){
        List<TopicWithSimpleResponse> postRequisites = topicService.getPostrequisitesByTopicId(id);
        return ResponseEntity.ok(postRequisites);
    }

    @GetMapping("/required-level/{id}")
    public ResponseEntity<List<TopicWhithLevelResponse>> getPrerrequisitesWhithLevelByTopicId(@PathVariable("id") String id){
        List<TopicWhithLevelResponse> prerrequisites = topicService.getPrerrequisitesWithLevelByTopicId(id);
        return ResponseEntity.ok(prerrequisites);
    }
}
