package org.tesis.modulodiagnostico.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.tesis.modulodiagnostico.dtos.request.EvaluateRecommendationRequest;
import org.tesis.modulodiagnostico.dtos.response.PlacementContinueResponse;
import org.tesis.modulodiagnostico.dtos.response.PlacementTestResponse;
import org.tesis.modulodiagnostico.services.impl.recommendations.EvaluationRecommendationService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/recommendation-evaluation")
public class RecommendationEvaluationController {

    private static final Logger log = LoggerFactory.getLogger(EvaluationRecommendationService.class);
    private final EvaluationRecommendationService evaluationRecommendationService;


    public RecommendationEvaluationController(EvaluationRecommendationService evaluationRecommendationService) {
        this.evaluationRecommendationService = evaluationRecommendationService;
    }

    @PostMapping
    public PlacementTestResponse evaluarRecomendacion(@RequestBody EvaluateRecommendationRequest request, @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        request.setUserId(userId);
        return this.evaluationRecommendationService.initEvaluationRecommendation(request);
    }

    /*@PutMapping("/{id}/submit")
    public BigDecimal submitPlacementTest(@PathVariable(name = "id") Long id, @RequestBody PlacementTestResponse placementTest) {
        log.info("Topic id: ",placementTest);
        log.info("User id: ",id);
        return this.evaluationRecommendationService.evaluateSingleTopicPlacement(id, placementTest);
    }*/

    @PutMapping("/submit")
    public BigDecimal submitPlacementTest(
            @RequestBody PlacementTestResponse placementTest,
            @AuthenticationPrincipal Jwt jwt
    ) {

        Long userId = jwt.getClaim("id");

        log.info("User ID desde token: {}", userId);

        return this.evaluationRecommendationService
                .evaluateSingleTopicPlacement(userId, placementTest);
    }
}
