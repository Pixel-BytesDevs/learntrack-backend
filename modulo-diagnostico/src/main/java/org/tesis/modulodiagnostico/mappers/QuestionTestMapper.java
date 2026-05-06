package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.response.QuestionTestResponse;
import org.tesis.modulodiagnostico.models.placementtests.QuestionTest;

import java.util.List;

@Component
public class QuestionTestMapper {

    private final DifficultyMapper difficultyMapper;
    private final AlternativeTestMapper alternativeTestMapper;

    public QuestionTestMapper(DifficultyMapper difficultyMapper, AlternativeTestMapper alternativeTestMapper) {
        this.difficultyMapper = difficultyMapper;
        this.alternativeTestMapper = alternativeTestMapper;
    }

    public QuestionTestResponse mapToQuestionTest(QuestionTest questionTest) {
        QuestionTestResponse response = new QuestionTestResponse();
        response.setId(questionTest.getId());
        response.setStatement(questionTest.getQuestionBank().getStatement());
        response.setTimeTakenInSeconds(-1);
        response.setAttempts(questionTest.getAttempts());
        response.setHintsUsed(questionTest.getHintsUsed());
        response.setIsCorrect(questionTest.getIsCorrect());
        response.setGradePenalized(
                questionTest.getGradePenalized() != null ? questionTest.getGradePenalized().doubleValue() : null
        );
        response.setDifficulty(difficultyMapper.mapToDifficultyResponse(questionTest.getQuestionBank().getDifficulty()));
        response.setAlternatives(alternativeTestMapper.mapToAlternativeTestResponses(questionTest.getAlternativeQuestionTests()));
        response.setTopicId(questionTest.getTopicTest().getId());
        return response;

    }

    public List<QuestionTestResponse> mapToQuestionTests(List<QuestionTest> questionTests) {
        return questionTests.stream()
                .map(this::mapToQuestionTest)
                .toList();
    }
}
