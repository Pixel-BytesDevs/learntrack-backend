package org.tesis.modulodiagnostico.services.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tesis.modulodiagnostico.dtos.extras.MessageRecommendation;
import org.tesis.modulodiagnostico.dtos.response.PrerecommendationData;

@Service
public class RecommedationClient {

    private static final Logger log = LoggerFactory.getLogger(RecommedationClient.class);

    private final RestTemplate restTemplate;

    @Value("${external.services.recommendation.base-url}")
    private String baseUrl;

    public RecommedationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MessageRecommendation initRecommendation(PrerecommendationData data) {
        String especificUrl = "/adaptability";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PrerecommendationData> entity = new HttpEntity<>(data, headers);
        try {
            ResponseEntity<MessageRecommendation> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    org.springframework.http.HttpMethod.POST,
                    entity,
                    MessageRecommendation.class
            );

            return response.getBody();
        } catch (RestClientException e) {
            log.error("ERROR AL COMUNICAR CON EL MOTOR DE RECOMENDACION {}", e.getMessage());
        }

        return null;
    }

    public MessageRecommendation finishRecommendations(Long userId) {

        String specificUrl = "/api/v1/recommendations/" + userId + "/finish";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<MessageRecommendation> response = restTemplate.exchange(
                    buildSafeUrl(specificUrl),
                    org.springframework.http.HttpMethod.POST,
                    entity,
                    MessageRecommendation.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("ERROR AL FINALIZAR RECOMENDACIONES {}", e.getMessage());
        }

        return null;
    }



    private String buildSafeUrl(String endpoint) {
        return baseUrl.endsWith("/")
                ? baseUrl + endpoint.replaceFirst("^/", "")
                : baseUrl + "/" + endpoint.replaceFirst("^/", "");
    }
}
