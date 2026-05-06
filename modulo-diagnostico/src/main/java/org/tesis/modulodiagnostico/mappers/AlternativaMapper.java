package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.CuestionarioAlternativaResponse;
import org.tesis.modulodiagnostico.models.cuestionariovark.Alternativa;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlternativaMapper {
    public CuestionarioAlternativaResponse toCuestionarioAlternativaResponse(Alternativa alternativa) {
        CuestionarioAlternativaResponse response = new CuestionarioAlternativaResponse();
        response.setAlternativaId(alternativa.getId());
        response.setAlternativa(alternativa.getAlternativaNombre());
        return response;
    }

    public List<CuestionarioAlternativaResponse> toCuestionarioAlternativaResponseList(List<Alternativa> alternativas) {
        return alternativas.stream()
                .map(this::toCuestionarioAlternativaResponse)
                .collect(Collectors.toList());
    }

}
