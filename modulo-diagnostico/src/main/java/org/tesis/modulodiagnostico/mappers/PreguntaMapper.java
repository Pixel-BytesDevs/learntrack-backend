package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.CuestionarioPreguntaResponse;
import org.tesis.modulodiagnostico.models.cuestionariovark.Pregunta;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PreguntaMapper {

    private final AlternativaMapper alternativaMapper;

    public PreguntaMapper(AlternativaMapper alternativaMapper) {
        this.alternativaMapper = alternativaMapper;
    }

    public CuestionarioPreguntaResponse toCuestionarioPreguntaResponse(Pregunta pregunta) {
        CuestionarioPreguntaResponse response = new CuestionarioPreguntaResponse();
        response.setPreguntaId(pregunta.getId());
        response.setSentencia(pregunta.getSentencia());
        response.setAlternativas(alternativaMapper.toCuestionarioAlternativaResponseList(pregunta.getAlternativas()));
        return response;
    }

    public List<CuestionarioPreguntaResponse> toCuestionarioPreguntaResponseList(List<Pregunta> preguntas) {
        return preguntas.stream()
                .map(this::toCuestionarioPreguntaResponse)
                .collect(Collectors.toList());
    }
}
