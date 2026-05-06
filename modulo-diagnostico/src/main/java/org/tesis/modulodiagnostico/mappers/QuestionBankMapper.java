package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.response.PlacementQuestionResponse;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;

import java.time.LocalDateTime;

@Component
public class QuestionBankMapper {

    public QuestionBank mapQuestionBack(PlacementQuestionResponse placementQuestionResponse, Difficulty difficulty, Topic topic) {
        QuestionBank questionBank = new QuestionBank();
        questionBank.setCreatedAt(LocalDateTime.now());
        questionBank.setLatexExpression(placementQuestionResponse.validateAndFixLatex());
        questionBank.setStatement(placementQuestionResponse.getSentence());
        questionBank.setDifficulty(difficulty);
        questionBank.setTopic(topic);
        return questionBank;
    }
}
