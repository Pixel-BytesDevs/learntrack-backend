package org.tesis.modulodiagnostico.services.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tesis.modulodiagnostico.dtos.request.PlacementQuestionAndAlternativeRequest;
import org.tesis.modulodiagnostico.dtos.response.GeminiResponse;
import org.tesis.modulodiagnostico.dtos.response.PlacementAlternativeResponse;
import org.tesis.modulodiagnostico.dtos.response.PlacementQuestionResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiClient {

    private final RestTemplate restTemplate;

    @Value("${external.services.gemini.base-url}")
    private String baseUrl;

    public GeminiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PlacementQuestionResponse> getPlacementQuestionsWithAlternatives(
            PlacementQuestionAndAlternativeRequest request
    ) {
        String specificUrl = "/prompt/test-nivel";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PlacementQuestionAndAlternativeRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<GeminiResponse<List<PlacementQuestionResponse>>> response = restTemplate.exchange(
                buildSafeUrl(specificUrl),
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<GeminiResponse<List<PlacementQuestionResponse>>>() {}
        );

        return response.getBody().getData();
    }
    private String buildSafeUrl(String endpoint) {
        return baseUrl.endsWith("/")
                ? baseUrl + endpoint.replaceFirst("^/", "")
                : baseUrl + "/" + endpoint.replaceFirst("^/", "");
    }
}
