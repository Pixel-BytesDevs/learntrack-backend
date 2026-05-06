package org.tesis.modulodiagnostico.services.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.tesis.modulodiagnostico.dtos.GraphDTO;
import org.tesis.modulodiagnostico.dtos.TopicGraphDTO;
import org.tesis.modulodiagnostico.dtos.request.TopicRequest;
import org.tesis.modulodiagnostico.exceptions.ExternalServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GraphClient {

    private static final Logger log = LoggerFactory.getLogger(GraphClient.class);
    private final RestTemplate restTemplate;

    @Value("${external.services.graph.base-url}")
    private String baseUrl;


    public GraphClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GraphDTO getGraphByCurriculoRef() {
        String especificUrl = "/graphs";
        try {
            ResponseEntity<GraphDTO> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    HttpMethod.GET,
                    null,
                    GraphDTO.class
            );
            return response.getBody();
        }catch (RestClientException e) {
            log.error("ERROR AL CONECTAR CON EL SERVICIO DE GRAFO: {}", e.getMessage(),e);
        }

        return null;
    }

    public List<TopicGraphDTO> getFirstFiveTopics() {
        String especificUrl = "/grafo-5";
        try {
            ResponseEntity<List<TopicGraphDTO>> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TopicGraphDTO>>() {}
            );

            List<TopicGraphDTO> topics = response.getBody();
            return topics != null ? topics : Collections.emptyList();
        }catch (RestClientException e) {
            log.error("ERROR AL CONECTAR CON EL SERVICIO DE GRAFO: {}", e.getMessage(),e);
            throw new ExternalServiceException();
        }
    }

    public List<TopicRequest> getPrerequisitesByTopicId(String topicId) {
        String especificUrl = "/topics/required/" + topicId;
        try {
            ResponseEntity<List<TopicRequest>> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TopicRequest>>() {}
            );

            List<TopicRequest> topics = response.getBody();
            return topics != null ? topics : Collections.emptyList();
        }catch (RestClientException e) {
            log.error("ERROR AL CONECTAR CON EL SERVICIO DE GRAFO: {}", e.getMessage(),e);
            throw new ExternalServiceException();
        }
    }

    public List<TopicRequest> getPostrequisitesByTopicId(String topicId) {
        String especificUrl = "/topics/post-required/" + topicId;
        try {
            ResponseEntity<List<TopicRequest>> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TopicRequest>>() {}
            );

            List<TopicRequest> topics = response.getBody();
            return topics != null ? topics : Collections.emptyList();
        }catch (RestClientException e) {
            log.error("ERROR AL CONECTAR CON EL SERVICIO DE GRAFO: {}", e.getMessage(),e);
            throw new ExternalServiceException();
        }
    }
    public List<TopicRequest> getAllTopics() {
        String especificUrl = "/topics";
        try {
            ResponseEntity<List<TopicRequest>> response = restTemplate.exchange(
                    buildSafeUrl(especificUrl),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TopicRequest>>() {}
            );

            List<TopicRequest> topics = response.getBody();
            return topics != null ? topics : Collections.emptyList();
        }catch (RestClientException e) {
            log.error("ERROR AL CONECTAR CON EL SERVICIO DE GRAFO: {}", e.getMessage(),e);
            throw new ExternalServiceException();
        }
    }



    private String buildSafeUrl(String endpoint) {
        return baseUrl.endsWith("/")
                ? baseUrl + endpoint.replaceFirst("^/", "")
                : baseUrl + "/" + endpoint.replaceFirst("^/", "");
    }


}
