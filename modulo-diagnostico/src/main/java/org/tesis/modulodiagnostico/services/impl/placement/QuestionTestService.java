package org.tesis.modulodiagnostico.services.impl.placement;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;
import org.tesis.modulodiagnostico.models.placementtests.QuestionTest;
import org.tesis.modulodiagnostico.models.placementtests.TopicTest;
import org.tesis.modulodiagnostico.repositories.QuestionsTestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionTestService {
    private final QuestionsTestRepository questionsTestRepository;

    public QuestionTestService(QuestionsTestRepository questionsTestRepository) {
        this.questionsTestRepository = questionsTestRepository;
    }

    public QuestionTest findById(Long id) {
        return questionsTestRepository.findById(id).orElseThrow(() -> new RuntimeException("QuestionTest not found with id: " + id));
    }

    public QuestionTest save(TopicTest topicTest, QuestionBank questionBank) {
        QuestionTest questionTest = new QuestionTest();
        questionTest.setQuestionBank(questionBank);
        questionTest.setTopicTest(topicTest);
        return questionsTestRepository.save(questionTest);
    }

    public List<QuestionTest> saveAll(List<QuestionBank> questionBanks, TopicTest topicTest) {
        List<QuestionTest> questionTests = new ArrayList<QuestionTest>();
        questionBanks.forEach(questionBank -> {
            QuestionTest questionTest = new QuestionTest();
            questionTest.setQuestionBank(questionBank);
            questionTest.setTopicTest(topicTest);
            questionTests.add(questionTest);
        });
        return questionTests;
    }
}
