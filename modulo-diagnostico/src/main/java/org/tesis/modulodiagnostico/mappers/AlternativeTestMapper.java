package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.response.AlternativeTestResponse;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeQuestionTest;

import java.util.List;

@Component
public class AlternativeTestMapper {

    public AlternativeTestResponse mapToAlternativeTestResponse(AlternativeQuestionTest alternativeQuestionTest) {
        AlternativeTestResponse response = new AlternativeTestResponse();
        response.setId(alternativeQuestionTest.getId());
        response.setLatexExpression(alternativeQuestionTest.getAlternativeBank().getLatexRepresentation());
        // Mapping logic goes here
        return response;
    }

    public List<AlternativeTestResponse> mapToAlternativeTestResponses(List<AlternativeQuestionTest> alternativeQuestionTests) {
        return alternativeQuestionTests.stream()
                .map(this::mapToAlternativeTestResponse)
                .toList();
    }
}
