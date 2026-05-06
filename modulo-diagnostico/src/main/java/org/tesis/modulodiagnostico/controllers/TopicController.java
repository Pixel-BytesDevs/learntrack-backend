package org.tesis.modulodiagnostico.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesis.modulodiagnostico.services.impl.placement.TopicServiceImpl;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicServiceImpl topicService;

    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/synchronize")
    public ResponseEntity<String> synchronizeTopics() {
        String topics = topicService.synchronizeTopics();
        return ResponseEntity.ok(topics);
    }
}
