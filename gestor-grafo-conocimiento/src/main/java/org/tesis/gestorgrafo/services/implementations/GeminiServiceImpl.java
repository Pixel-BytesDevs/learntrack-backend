package org.tesis.gestorgrafo.services.implementations;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.dtos.base.ResponseAPI;
import org.tesis.gestorgrafo.services.IGeminiService;

import java.util.HashMap;
import java.util.Map;

@Service
public class GeminiServiceImpl implements IGeminiService {

    private final RestTemplate restTemplate;


    public GeminiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public ResponseAPI<CompetenciaDTO> getCompentecia(String url) {
        // Creamos un objeto que contiene el URL como parte del cuerpo
        Map<String, String> body = new HashMap<String, String>();
        body.put("documentoUrl", url);

        // Realizamos una solicitud POST con el cuerpo como JSON
        return restTemplate.exchange(
                "http://0.0.0.0:8000/prompt/grafo",
                HttpMethod.POST,
                new HttpEntity<>(body),
                new ParameterizedTypeReference<ResponseAPI<CompetenciaDTO>>() {}
        ).getBody();
    }



}
