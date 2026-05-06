package org.tesis.modulodiagnostico.services.impl.placement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tesis.modulodiagnostico.dtos.extras.MessageRecommendation;
import org.tesis.modulodiagnostico.dtos.extras.QuestionSaveResult;
import org.tesis.modulodiagnostico.dtos.request.PlacementQuestionAndAlternativeRequest;
import org.tesis.modulodiagnostico.dtos.response.*;
import org.tesis.modulodiagnostico.mappers.PlacementTestMapper;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.*;
import org.tesis.modulodiagnostico.repositories.PlacementTestRepository;
import org.tesis.modulodiagnostico.services.clients.GeminiClient;
import org.tesis.modulodiagnostico.services.impl.TopicUserService;
import org.tesis.modulodiagnostico.services.impl.UserServiceImpl;
import org.tesis.modulodiagnostico.services.impl.recommendations.RecommendationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class PlacementTestServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(PlacementTestServiceImpl.class);
    // private static final org.slf4j.Logger log = LoggerFactory.getLogger(GeminiBatchService.class);
    private final GeminiClient geminiClient;
    private final PlacementTestRepository placementTestRepository;
    private final QuestionBankService questionBankService;
    private final TopicServiceImpl topicService;
    private final AlternativeBankService alternativeBankService;
    private final UserServiceImpl userService;
    private final PlacementTestMapper placementTestMapper;
    private final TopicTestServiceImpl topicTestService;
    private final QuestionTestService questionTestService;
    private final AlternativeTestService alternativeTestService;
    private final TopicUserService topicUserService;
    private final RecommendationService recommendationService;

    public PlacementTestServiceImpl(GeminiClient geminiClient, PlacementTestRepository placementTestRepository, QuestionBankService questionBankService, TopicServiceImpl topicService, AlternativeBankService alternativeBankService, UserServiceImpl userService, PlacementTestMapper placementTestMapper, TopicTestServiceImpl topicTestService, QuestionTestService questionTestService, AlternativeTestService alternativeTestService, TopicUserService topicUserService, RecommendationService recommendationService) {
        this.geminiClient = geminiClient;
        this.placementTestMapper = placementTestMapper;
        this.placementTestRepository = placementTestRepository;
        this.questionBankService = questionBankService;
        this.topicService = topicService;
        this.alternativeBankService = alternativeBankService;
        this.userService = userService;
        this.topicTestService = topicTestService;
        this.questionTestService = questionTestService;
        this.alternativeTestService = alternativeTestService;
        this.topicUserService = topicUserService;
        this.recommendationService = recommendationService;
    }

    @Transactional
    public List<QuestionSaveResult> fillQuestionAndAlernativesFromGemini(PlacementQuestionAndAlternativeRequest request) {


        List<QuestionSaveResult> questionSaveResults = new ArrayList<>();


        List<PlacementQuestionResponse> result = geminiClient.getPlacementQuestionsWithAlternatives(request);
        log.info("Paso gemini client");
        for (PlacementQuestionResponse placementQuestionResponse : result) {
            try {
                questionBankService.create(placementQuestionResponse);
                questionSaveResults.add(new QuestionSaveResult(placementQuestionResponse.getSentence(), true, "GUARDO CORRECTAMENTE"));
            } catch (Exception e) {
                questionSaveResults.add(new QuestionSaveResult(placementQuestionResponse.getSentence(), false, e.getMessage()));
            }
        }

        return questionSaveResults;
    }

    // TODO: Completar este método
    public List<PlacementQuestionWithIdResponse> createPlacementTestForStudent() {

        List<Topic> randomTopics = topicService.getRandomTopics();
        List<QuestionBank> originalQuestions = new ArrayList<>();
        List<AlternativeBank> originalAlternatives = new ArrayList<>();
        List<PlacementQuestionWithIdResponse> questions = new ArrayList<>();

        randomTopics.forEach(topic -> {
            originalQuestions.addAll(this.questionBankService.findAllByTopic(topic).subList(0, 3));
        });

        originalQuestions.forEach(question -> {
            originalAlternatives.addAll(this.alternativeBankService.findAllByQuestion(question));
        });

        originalQuestions.forEach(question -> {
            PlacementQuestionWithIdResponse placementQuestionWithIdResponse = toPlacementQuestionWithId(question);
            List<AlternativeBank> alternatives = originalAlternatives.stream().filter(alternativeBank -> alternativeBank.getQuestionBank().equals(question)).toList();
            List<PlacementAlternativeWithIdResponse> s = alternatives.stream().map(this::toPlacementAlternativeWithId).toList();
            placementQuestionWithIdResponse.setAlternatives(s);
            questions.add(placementQuestionWithIdResponse);
        });

        return questions;
    }

    private PlacementQuestionWithIdResponse toPlacementQuestionWithId(QuestionBank questionBank) {
        PlacementQuestionWithIdResponse placementQuestionWithIdResponse = new PlacementQuestionWithIdResponse();
        placementQuestionWithIdResponse.setId(questionBank.getId());
        if (questionBank.getDifficulty() != null) {
            placementQuestionWithIdResponse.setDifficulty(questionBank.getDifficulty().getName().name());
        }
        placementQuestionWithIdResponse.setExpressionLatex(questionBank.getLatexExpression());
        placementQuestionWithIdResponse.setSentence(questionBank.getStatement());
        return placementQuestionWithIdResponse;

    }

    private PlacementAlternativeWithIdResponse toPlacementAlternativeWithId(AlternativeBank alternativeBank) {
        PlacementAlternativeWithIdResponse placementAlternativeWithIdResponse = new PlacementAlternativeWithIdResponse();
        placementAlternativeWithIdResponse.setId(alternativeBank.getId());
        placementAlternativeWithIdResponse.setCorrect(alternativeBank.getCorrect());
        placementAlternativeWithIdResponse.setText(alternativeBank.getText());
        placementAlternativeWithIdResponse.setLatex(alternativeBank.getLatexRepresentation());
        return placementAlternativeWithIdResponse;
    }

    public PlacementTest createPlacementTest() {
        PlacementTest placementTest = new PlacementTest();
        placementTest.setStartedAt(LocalDateTime.now());
        placementTest.setDurationInSeconds(60 * 15);
        return placementTestRepository.save(placementTest);
    } 


    // Para iniciar el test de nivel de aprendizaje inicial
    @Transactional
    public PlacementTestResponse startPlacementTest(Long userId) {

        // Verificar que el usuario exista
        /*if (userService.findById(userId) == null) {
            throw new UserNotFoundException();
        }*/

        // Obtenemos el usuario
        //Usuario usuario = userService.findById(userId);
        // Crear la entidad Placement test que indica el inicio del test
        PlacementTest test = new PlacementTest();
        test.setPlacementType(PlacementTypes.INITIAL);
        test.setStartedAt(LocalDateTime.now());
        test.setDurationInSeconds(15 * 60);
        //test.setUsuario(usuario);
        test.setUsuarioId(userId);

        //guardamos el placement test
        PlacementTest testCreated = placementTestRepository.save(test);

        // INITIAL: abarcar todos los tópicos marcados como principales.
        List<Topic> principalTopics = topicService.getPrincipalTopics();
        System.out.println("PRINCIPAL TOPICS one: " + principalTopics.get(0).getName());
        List<TopicTest> topicTests = new ArrayList<>();
        List<QuestionTest> questionsTest = new ArrayList<>();

        for (Topic topic : principalTopics) {
            log.info("topic id "+ topic.getId());
            List<QuestionBank> questions = questionBankService.getInitialPlacementQuestionsByTopic(topic);
            for(QuestionBank q : questions){
                System.out.println("Question: "+q.getId());
            }
            System.out.println("QUESTIONS: " + questions.get(0).getTopic().getName());
            if (questions == null || questions.isEmpty()) {
                System.out.println("TEST VACIO");
                continue;
            }

            TopicTest topicTest = new TopicTest();
            topicTest.setPlacementTest(testCreated);
            topicTest.setTopic(topic);
            topicTest.setNumberOfQuestions(questions.size());
            TopicTest persistedTopicTest = topicTestService.save(topicTest);

            List<QuestionTest> savedQuestions = questionTestService.saveAll(questions, persistedTopicTest);
            persistedTopicTest.setQuestionTests(savedQuestions);
            TopicTest topicTestWithQuestions = topicTestService.save(persistedTopicTest);

            topicTests.add(topicTestWithQuestions);
            questionsTest.addAll(savedQuestions);
        }

        testCreated.setTopicTests(topicTests);

        List<AlternativeQuestionTest> alternativeQuestionTests = new ArrayList<>();
        // obtener alternativas
        questionsTest.forEach(questionTest -> {
            List<AlternativeBank> alternatives = alternativeBankService.findAllByQuestion(
                    questionTest.getQuestionBank()
            );
            Collections.shuffle(alternatives);
            List<AlternativeQuestionTest> alternativeSaved = alternativeTestService.saveAll(alternatives, questionTest);
            questionTest.setAlternativeQuestionTests(alternativeSaved);
            alternativeQuestionTests.addAll(alternativeSaved);
        });

        //return placementTestMapper.mapPlacementTestResponse(testCreated);
        return placementTestMapper.mapPlacementTestResponse(testCreated);
    }

    @Transactional
    public ResultLevelTestResponse submitPlacementTest(Long userId, PlacementTestResponse placementTestResponse) {
        ResultLevelTestResponse resultLevelTestResponse = new ResultLevelTestResponse();
        List<ScoreAndTopicResponse> scoreAndTopicResponses = new ArrayList<>();
        PlacementTest placementTest = placementTestRepository.findById(placementTestResponse.getId())
                .orElseThrow(() -> new RuntimeException("Placement test not found"));

        List<TopicTest> topics = topicTestService.findAllByPlacementTest(placementTest);
        Map<TopicTest, List<QuestionTestResponse>> topicTestResponses = new LinkedHashMap<>();

        topics.forEach(topic -> {
            List<QuestionTestResponse> questionTestResponses = placementTestResponse.getQuestionTestResponses().stream().filter(q -> q.getTopicId().equals(topic.getId()))
                    .toList();
            topicTestResponses.put(topic, questionTestResponses);
        });
        List<BigDecimal> perTopicScores012 = new ArrayList<>();

        topicTestResponses.forEach((topic, questions) -> {
            System.out.println("📚 Evaluando tema: " + topic.getTopic().getName() + topic.getTopic().getId());

            BigDecimal topicScore012 = BigDecimal.ZERO;
            for (QuestionTestResponse question : questions) {
                AlternativeTestResponse alternativeMarked = question.getAlternatives() == null ? null
                        : question.getAlternatives().stream()
                        .filter(a -> Boolean.TRUE.equals(a.getSelected()))
                        .findFirst()
                        .orElse(null);

                boolean isCorrect = false;
                if (alternativeMarked != null) {
                    AlternativeQuestionTest alternativeQuestionTest = alternativeTestService.findById(alternativeMarked.getId());
                    isCorrect = Boolean.TRUE.equals(alternativeQuestionTest.getAlternativeBank().getCorrect());
                }

                int attempts = clampAttempt(question.getAttempts());
                int hintsUsed = clampHints(question.getHintsUsed());
                BigDecimal gp = calculatePenalizedGrade(isCorrect, attempts, hintsUsed); // legacy persistencia

                // Scoring 0..12: si es correcta suma el peso de su dificultad (EASY=1, MEDIUM=2, HARD=3)
                if (Boolean.TRUE.equals(isCorrect)) {
                    QuestionTest persisted = questionTestService.findById(question.getId());
                    Integer weight = persisted.getQuestionBank().getDifficulty().getName().getWeigth();
                    if (weight != null) {
                        topicScore012 = topicScore012.add(BigDecimal.valueOf(weight));
                    }
                }

                // Persistimos el desempeño por ítem en questions_tests.
                QuestionTest questionTest = questionTestService.findById(question.getId());
                questionTest.setTimeTakenInSeconds(question.getTimeTakenInSeconds());
                questionTest.setAttempts(attempts);
                questionTest.setHintsUsed(hintsUsed);
                questionTest.setIsCorrect(isCorrect);
                questionTest.setGradePenalized(gp);
            }

            ScoreAndTopicResponse scoreAndTopicResponse = new ScoreAndTopicResponse();
            scoreAndTopicResponse.setScore(topicScore012.setScale(2, RoundingMode.HALF_UP));
            scoreAndTopicResponse.setNameTopic(topic.getTopic().getName());
            scoreAndTopicResponses.add(scoreAndTopicResponse);
            perTopicScores012.add(topicScore012);
        });

        resultLevelTestResponse.setScores(scoreAndTopicResponses);

        // Promedio global del test inicial: 0..12
        BigDecimal avg012 = calculateAverage012(perTopicScores012);
        topicUserService.initializePositionAfterInitialTest(userId, avg012);

        // Mantener compatibilidad con UI: devolver un nombre de primer tema.
        MessageRecommendation messageRecommendation = this.recommendationService.initRecommendation(userId);
        resultLevelTestResponse.setNameFirsTopic(messageRecommendation.getTema());
        return resultLevelTestResponse;
    }

    private BigDecimal calculateAverage012(List<BigDecimal> perTopicScores) {
        if (perTopicScores == null || perTopicScores.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal sum = perTopicScores.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(perTopicScores.size()), 2, RoundingMode.HALF_UP);
    }


    private BigDecimal calculatePenalizedGrade(boolean isCorrect, int attempts, int hintsUsed) {
        if (!isCorrect || attempts >= 4) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal base = BigDecimal.ONE;
        BigDecimal penalty = BigDecimal.valueOf(hintsUsed).multiply(BigDecimal.valueOf(0.2));
        BigDecimal gp = base.subtract(penalty);
        if (gp.signum() < 0) {
            gp = BigDecimal.ZERO;
        }
        return gp.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTopicScoreFromPenalizedGrades(List<BigDecimal> penalizedGrades) {
        if (penalizedGrades.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        BigDecimal avgGp = penalizedGrades.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(penalizedGrades.size()), 4, RoundingMode.HALF_UP);

        BigDecimal adjustedGp = applyPerformanceThreshold(avgGp); // 0..1
        return adjustedGp.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyPerformanceThreshold(BigDecimal avgGp) {
        if (avgGp.compareTo(BigDecimal.valueOf(0.8)) >= 0) {
            // Excelencia: promoción fuerte (+2 niveles).
            return avgGp.add(BigDecimal.valueOf(0.20)).min(BigDecimal.ONE);
        }
        if (avgGp.compareTo(BigDecimal.valueOf(0.4)) >= 0) {
            // Suficiente: mejora gradual (+1 nivel), con ajuste más generoso para evitar castigos excesivos.
            return avgGp.add(BigDecimal.valueOf(0.20)).min(BigDecimal.ONE);
        }
        // Insuficiente: se mantiene base para refuerzo, sin degradación extra.
        return avgGp;
    }

    private int clampAttempt(Integer attempts) {
        if (attempts == null) {
            return 1;
        }
        return Math.max(0, Math.min(4, attempts));
    }

    private int clampHints(Integer hintsUsed) {
        if (hintsUsed == null) {
            return 0;
        }
        return Math.max(0, Math.min(4, hintsUsed));
    }



}
