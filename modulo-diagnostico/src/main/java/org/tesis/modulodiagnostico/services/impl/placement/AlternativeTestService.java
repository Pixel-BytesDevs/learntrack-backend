package org.tesis.modulodiagnostico.services.impl.placement;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeBank;
import org.tesis.modulodiagnostico.models.placementtests.AlternativeQuestionTest;
import org.tesis.modulodiagnostico.models.placementtests.QuestionTest;
import org.tesis.modulodiagnostico.repositories.AlternativeTestRepository;

import java.util.List;

@Service
public class AlternativeTestService {

    private final AlternativeTestRepository alternativeTestRepository;

    public AlternativeTestService(AlternativeTestRepository alternativeTestRepository) {
        this.alternativeTestRepository = alternativeTestRepository;
    }

    public AlternativeQuestionTest findById(Long id) {
        return alternativeTestRepository.findById(id).orElseThrow(() -> new RuntimeException("AlternativeQuestionTest not found with id: " + id));
    }

    public List<AlternativeQuestionTest> saveAll(List<AlternativeBank> alternativeTests, QuestionTest questionTest) {
        List<AlternativeQuestionTest> alternativeQuestionTests = alternativeTests.stream().map(
                alternativeBank -> {
                    AlternativeQuestionTest alternativeQuestionTest = new AlternativeQuestionTest();
                    alternativeQuestionTest.setAlternativeBank(alternativeBank);
                    alternativeQuestionTest.setQuestionTest(questionTest);
                    return alternativeQuestionTest;
                }
        ).toList();
        return alternativeTestRepository.saveAll(alternativeQuestionTests);
    }
}
