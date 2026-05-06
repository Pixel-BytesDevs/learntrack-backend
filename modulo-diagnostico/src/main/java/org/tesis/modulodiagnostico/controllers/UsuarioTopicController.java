package org.tesis.modulodiagnostico.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.tesis.modulodiagnostico.dtos.response.ActiveTopicsResponse;
import org.tesis.modulodiagnostico.dtos.response.ProgressGraph;
import org.tesis.modulodiagnostico.services.impl.TopicUserService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario-topic")
public class UsuarioTopicController {

    private final TopicUserService topicUserService;

    public UsuarioTopicController(TopicUserService topicUserService) {
        this.topicUserService = topicUserService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<?> initializeTopicUserForAnUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");

        String result = topicUserService.initializeTopicUserForAnUser(userId);

        return ResponseEntity.ok(Map.of("message", result, "userId", userId, "status", "success"));
    }

    @GetMapping("/progress-graph")
    public List<ProgressGraph> getProgressGraphByUserId(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return topicUserService.findAllByUserId(userId);
    }

    @GetMapping("/active-topics")
    public ResponseEntity<List<ActiveTopicsResponse>> getActiveTopics(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");

        List<ActiveTopicsResponse> response = topicUserService.getActiveTopicsByUserId(userId);

        return ResponseEntity.ok(response);
    }

}
