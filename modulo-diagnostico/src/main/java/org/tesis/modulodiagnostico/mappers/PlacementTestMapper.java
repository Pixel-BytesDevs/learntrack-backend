package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.response.PlacementTestResponse;
import org.tesis.modulodiagnostico.dtos.response.QuestionTestResponse;
import org.tesis.modulodiagnostico.models.placementtests.PlacementTest;
import org.tesis.modulodiagnostico.models.placementtests.QuestionTest;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlacementTestMapper {

    private final QuestionTestMapper questionTestMapper;

    public PlacementTestMapper(QuestionTestMapper questionTestMapper) {
        this.questionTestMapper = questionTestMapper;
    }

    public PlacementTestResponse mapPlacementTestResponse(PlacementTest placementTest) {
        PlacementTestResponse response = new PlacementTestResponse();
        response.setId(placementTest.getId());
        response.setDuration(placementTest.getDurationInSeconds());
        response.setStartedAt(placementTest.getStartedAt());
        //response.setUserId(placementTest.getUsuario().getId());
        response.setUserId(placementTest.getUsuarioId());

        List<QuestionTest> questionTests = placementTest.getTopicTests().stream()
                .flatMap(question -> question.getQuestionTests().stream())
                .toList();

        response.setQuestionTestResponses(questionTestMapper.mapToQuestionTests(questionTests));
        return response;
    }

}
