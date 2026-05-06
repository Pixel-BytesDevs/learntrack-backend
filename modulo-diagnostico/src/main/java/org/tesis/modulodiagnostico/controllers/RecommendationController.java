package org.tesis.modulodiagnostico.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesis.modulodiagnostico.dtos.extras.MessageRecommendation;
import org.tesis.modulodiagnostico.services.impl.recommendations.RecommendationService;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping()
    public MessageRecommendation simulationRecommendation(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return recommendationService.initRecommendation(userId);
    }
}
