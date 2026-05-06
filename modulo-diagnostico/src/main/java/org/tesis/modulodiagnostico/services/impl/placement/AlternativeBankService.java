package org.tesis.modulodiagnostico.services.impl.placement;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.response.PlacementAlternativeResponse;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeBank;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;
import org.tesis.modulodiagnostico.repositories.AlternativeBankRepository;

import java.util.List;

@Service
public class AlternativeBankService {
    private final AlternativeBankRepository alternativeBankRepository;

    public AlternativeBankService(AlternativeBankRepository alternativeBankRepository) {
        this.alternativeBankRepository = alternativeBankRepository;
    }

    public AlternativeBank create(PlacementAlternativeResponse response, QuestionBank questionBank) {
        AlternativeBank alternativeBank = new AlternativeBank();
        alternativeBank.setText(response.getText());
        alternativeBank.setCorrect(response.getCorrect());
        alternativeBank.setLatexRepresentation(response.validateAndFixLatex());
        alternativeBank.setQuestionBank(questionBank);
        return alternativeBankRepository.save(alternativeBank);
    }

    public List<AlternativeBank> findAllByQuestion(QuestionBank questionBank) {
        return this.alternativeBankRepository.findAllByQuestionBank(questionBank);
    }

}
