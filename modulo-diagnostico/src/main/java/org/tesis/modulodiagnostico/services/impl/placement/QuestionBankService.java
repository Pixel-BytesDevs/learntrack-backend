package org.tesis.modulodiagnostico.services.impl.placement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tesis.modulodiagnostico.dtos.response.PlacementQuestionResponse;
import org.tesis.modulodiagnostico.mappers.QuestionBankMapper;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.Difficulties;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;
import org.tesis.modulodiagnostico.models.placementtests.QuestionBank;
import org.tesis.modulodiagnostico.repositories.QuestionBankRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionBankService {


    private static final Logger log = LoggerFactory.getLogger(QuestionBankService.class);
    private final QuestionBankRepository questionBankRepository;
    private final QuestionBankMapper questionBankMapper;
    private final TopicServiceImpl topicService;
    private final DifficultyService difficultyService;
    private final AlternativeBankService alternativeBankService;

    public QuestionBankService(QuestionBankRepository questionBankRepository, QuestionBankMapper questionBankMapper, TopicServiceImpl topicService, DifficultyService difficultyService, AlternativeBankService alternativeBankService) {
        this.questionBankRepository = questionBankRepository;
        this.questionBankMapper = questionBankMapper;
        this.topicService = topicService;
        this.difficultyService = difficultyService;
        this.alternativeBankService = alternativeBankService;
    }


    @Transactional
    public QuestionBank create(PlacementQuestionResponse response) {
        try {
            Topic topic = topicService.getById(response.getTopicId());
            Difficulty difficulty = this.difficultyService.getDifficultyByName(response.getDifficulty());
            QuestionBank questionBank = questionBankMapper.mapQuestionBack(response, difficulty, topic);
            QuestionBank saved = questionBankRepository.save(questionBank);
            response.getAlternatives().forEach(alternative -> alternativeBankService.create(alternative, saved));
            log.info("PREGUNTA GUARDADA CORRECTAMENTE CON UN ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            log.error("ERROR AL GUARDAR LA PREGUNTA: '{}':{}", response.getTopicId(), e.getMessage());
            throw new RuntimeException("Error al guardar la pregunta y sus alternativas", e);
        }
    }

    public QuestionBank findById(Long id) {
        return questionBankRepository.findById(id).orElseThrow(() -> new RuntimeException("QuestionBank not found with id: " + id));
    }

    public List<QuestionBank> findAllByTopic(Topic topic) {
        return questionBankRepository.findAllByTopic(topic);
    }

    public List<QuestionBank> findTwoRandomQuestionsByDifficultyAndTopic(Difficulty difficulty, Topic topic,Integer max) {
        if(max == 2) {
            return   questionBankRepository.findTwoRandomQuestionsByDifficultyAndTopic(difficulty.getId(), topic.getId());
        }else {
            return   questionBankRepository.findFourRandomQuestionsByDifficultyAndTopic(difficulty.getId(), topic.getId());
        }

    }

    public List<QuestionBank> getQuestionsForPlacementTestByTopic(Topic topic, Integer max) {
        List<Difficulty> difficulties = difficultyService.getAllDifficulties();
        List<QuestionBank> questionBanks = new ArrayList<>();
        difficulties.forEach(difficulty -> {
            questionBanks.addAll(findTwoRandomQuestionsByDifficultyAndTopic(difficulty, topic,max));
        });
        return questionBanks;
    }

    /**
     * Placement inicial: hasta 6 preguntas por tópico principal (las que existan en banco):
     * - 2 EASY (básico), si hay
     * - 2 MEDIUM (intermedio), si hay
     * - 2 HARD (avanzado), si hay
     * Si no hay suficientes de un nivel, se toma lo disponible.
     */
    public List<QuestionBank> getInitialPlacementQuestionsByTopic(Topic topic) {
        Difficulty easy = difficultyService.getDifficultyByName(Difficulties.EASY.name());
        Difficulty medium = difficultyService.getDifficultyByName(Difficulties.MEDIUM.name());
        Difficulty hard = difficultyService.getDifficultyByName(Difficulties.HARD.name());

        List<QuestionBank> selected = new ArrayList<>(6);
        selected.addAll(pickRandomQuestions(topic, easy, 2));
        selected.addAll(pickRandomQuestions(topic, medium, 2));
        selected.addAll(pickRandomQuestions(topic, hard, 2));
        return selected;
    }

    private List<QuestionBank> pickRandomQuestions(Topic topic, Difficulty difficulty, int limit) {
        List<QuestionBank> picked = questionBankRepository.findRandomByTopicIdAndDifficultyId(
                topic.getId(),
                difficulty.getId(),
                limit
        );
        return picked == null ? List.of() : picked;
    }
}
