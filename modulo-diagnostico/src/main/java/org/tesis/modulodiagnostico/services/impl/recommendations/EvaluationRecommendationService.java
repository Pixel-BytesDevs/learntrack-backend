package org.tesis.modulodiagnostico.services.impl.recommendations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tesis.modulodiagnostico.dtos.request.EvaluateRecommendationRequest;
import org.tesis.modulodiagnostico.dtos.response.AlternativeTestResponse;
import org.tesis.modulodiagnostico.dtos.response.PlacementTestResponse;
import org.tesis.modulodiagnostico.dtos.response.QuestionTestResponse;
import org.tesis.modulodiagnostico.mappers.PlacementTestMapper;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.*;
import org.tesis.modulodiagnostico.models.recommendations.ContinuousRecommendationAssignment;
import org.tesis.modulodiagnostico.models.usuarios.TopicUser;
import org.tesis.modulodiagnostico.repositories.ContinuousRecommendationAssignmentRepository;
import org.tesis.modulodiagnostico.repositories.PlacementTestRepository;
import org.tesis.modulodiagnostico.repositories.TopicUserRepository;
import org.tesis.modulodiagnostico.services.impl.TopicUserService;
import org.tesis.modulodiagnostico.services.impl.placement.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EvaluationRecommendationService {
    private static final Logger log = LoggerFactory.getLogger(EvaluationRecommendationService.class);
    @Autowired
    private PlacementTestRepository placementTestRepository;
    @Autowired
    private TopicServiceImpl topicService;
    @Autowired
    private TopicTestServiceImpl topicTestService;
    @Autowired
    private PlacementTestMapper placementTestMapper;

    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionTestService questionTestService;
    @Autowired
    private AlternativeTestService alternativeTestService;
    @Autowired
    private AlternativeBankService alternativeBankService;

    @Autowired
    private TopicUserService topicUserService;

    @Autowired
    private TopicUserRepository topicUserRepository;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private ContinuousRecommendationAssignmentRepository assignmentRepository;

    public PlacementTestResponse initEvaluationRecommendation(EvaluateRecommendationRequest request) {
        /*if(userService.findById(request.getUserId()) == null) {
            throw new UserNotFoundException();
        }*/

        //Usuario usuario = userService.findById(request.getUserId());

        PlacementTest test = new PlacementTest();
        test.setPlacementType(PlacementTypes.CONTINUE);
        //test.setUsuario(usuario);
        test.setUsuarioId(request.getUserId());
        test.setStartedAt(LocalDateTime.now());
        test.setDurationInSeconds(15*60);

        PlacementTest placementTest = placementTestRepository.save(test);

        Topic topic = topicService.getById(request.getTopicId());

        TopicTest topicTest = topicTestService.createTopicTestsForPlacementTest(placementTest, List.of(topic)).getFirst();
        placementTest.getTopicTests().clear();
        placementTest.getTopicTests().add(topicTest);


        List<QuestionBank> questionTests = questionBankService.getQuestionsForPlacementTestByTopic(topic, 4);
        List<QuestionTest> savedQuestionTests = questionTestService.saveAll(questionTests,topicTest);
        topicTest.setQuestionTests(savedQuestionTests);

        List<AlternativeQuestionTest> alternativeQuestionTests = new ArrayList<>();
        savedQuestionTests.forEach(questionTest -> {
            List<AlternativeBank> alternatives = alternativeBankService.findAllByQuestion(
                    questionTest.getQuestionBank()
            );
            Collections.shuffle(alternatives);
            List<AlternativeQuestionTest> alternativeSaved = alternativeTestService.saveAll(alternatives, questionTest);
            questionTest.setAlternativeQuestionTests(alternativeSaved);
            alternativeQuestionTests.addAll(alternativeSaved);
        });

        TopicUser topicUser = topicUserRepository.findByTopicIdAndUserId(topic.getId(), request.getUserId());
        BigDecimal currentDomain = topicUser == null ? BigDecimal.ZERO : normalizeDomainScale(topicUser.getDomainLevel());
        Difficulties recommendedDifficulty = mapDifficultyByDomain(currentDomain);

        ContinuousRecommendationAssignment assignment = new ContinuousRecommendationAssignment();
        assignment.setUserId(request.getUserId());
        assignment.setTopicId(topic.getId());
        assignment.setPlacementTestId(placementTest.getId());
        assignment.setRecommendedDifficulty(recommendedDifficulty);
        assignment.setCreatedAt(LocalDateTime.now());
        assignmentRepository.save(assignment);


        return placementTestMapper.mapPlacementTestResponse(test);
    }



    @Transactional
    public BigDecimal evaluateSingleTopicPlacement(
            Long userId,
            PlacementTestResponse placementTestResponse
        ) {
        log.info("EVALUANDO PLACEMENT TEST ID: {} PARA USUARIO ID: {}", placementTestResponse.getId(), userId);
        PlacementTest test = placementTestRepository
                .findByIdAndUsuarioId(placementTestResponse.getId(), userId)
                .orElseThrow(() -> new RuntimeException(
                        "PlacementTest not found with id " + placementTestResponse.getId() + " for user " + userId
                ));
        String topicId = test.getTopicTests().get(0).getTopic().getId();
        log.info("TOPIC ID ASOCIADO AL PLACEMENT TEST: {}", topicId);

        // APPEAL: calcular Gp por pregunta usando correcto + intentos + hints.
        List<QuestionTestResponse> preguntas = placementTestResponse.getQuestionTestResponses();
        log.info("TOTAL DE PREGUNTAS EN DTO DE RESPUESTA: {}", preguntas.size());
        List<BigDecimal> penalizedGrades = new ArrayList<>();

        for (QuestionTestResponse q : preguntas) {
            AlternativeTestResponse marked = q.getAlternatives() == null ? null :
                    q.getAlternatives().stream()
                            .filter(a -> Boolean.TRUE.equals(a.getSelected()))
                            .findFirst()
                            .orElse(null);

            log.info("PROCESANDO PREGUNTA ID: {} - ALTERNATIVA MARCADA: {}",
                    q.getId(),
                    marked == null ? "Ninguna" : marked.getId()
            );

            boolean isCorrect = false;
            if (marked != null) {
                AlternativeQuestionTest altPersisted = alternativeTestService.findById(marked.getId());
                isCorrect = altPersisted != null && Boolean.TRUE.equals(altPersisted.getAlternativeBank().getCorrect());
            }

            int attempts = clampAttempt(q.getAttempts());
            int hintsUsed = clampHints(q.getHintsUsed());
            BigDecimal gp = calculatePenalizedGrade(isCorrect, attempts, hintsUsed);
            penalizedGrades.add(gp);

            QuestionTest questionTest = questionTestService.findById(q.getId());
            questionTest.setTimeTakenInSeconds(q.getTimeTakenInSeconds());
            questionTest.setAttempts(attempts);
            questionTest.setHintsUsed(hintsUsed);
            questionTest.setIsCorrect(isCorrect);
            questionTest.setGradePenalized(gp);
        }

        TopicUser topicUser = topicUserRepository.findByTopicIdAndUserId(topicId,userId);
        BigDecimal currentDomain = normalizeDomainScale(topicUser.getDomainLevel());
        log.info("DOMINIO ACTUAL {}", currentDomain);

        BigDecimal testDomain = calculateDomainFromPenalizedGrades(penalizedGrades);
        //BigDecimal dominio = mergeWithCurrentDomain(currentDomain, testDomain);
        BigDecimal delta = calculateDeltaFromTest(testDomain);
        BigDecimal dominio = currentDomain.add(delta);
        log.info("dominio actual(%): {}, dominio test(APPEAL %): {}, nuevoValor(%): {}",
                currentDomain, testDomain, dominio);

        // Si el usuario resuelve correctamente el test del OA, incrementa su dominio según el nivel
        // asociado a su rango actual: bajo(+7), intermedio(+10), alto(+15).
        //BigDecimal finalDomain = dominio;
        BigDecimal finalDomain = BigDecimal.valueOf(
                clampPercentage(dominio.doubleValue())
        ).setScale(2, RoundingMode.HALF_UP);

        if (testDomain.compareTo(new BigDecimal("90.00")) >= 0) {
            finalDomain = finalDomain.add(new BigDecimal("2.00"));
        }

        topicUserService.setInitialDomainInANodeByUser(topicId, userId, finalDomain);
        this.recommendationService.finishRecommendations(userId);
        this.recommendationService.initRecommendation(userId);

        return finalDomain;
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

    private BigDecimal calculateDomainFromPenalizedGrades(List<BigDecimal> penalizedGrades) {
        if (penalizedGrades == null || penalizedGrades.isEmpty()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal avgGp01 = penalizedGrades.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(penalizedGrades.size()), 4, RoundingMode.HALF_UP);
        return avgGp01.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

    /*private BigDecimal mergeWithCurrentDomain(BigDecimal currentDomain, BigDecimal testDomain) {
        double cd = clampPercentage(currentDomain.doubleValue());
        double td = clampPercentage(testDomain.doubleValue());
        double nuevoValor;

        if (td >= cd) {
            // Mejora gradual: avanzar solo una fracción de la brecha hacia el resultado del test.
            // Esto evita saltos bruscos a 100% por un único intento.
            nuevoValor = cd + ((td - cd) * 0.25);
        } else {
            // Retroceso suave cuando el test sale peor que el dominio actual.
            nuevoValor = cd - ((cd - td) * 0.10);
        }

        return BigDecimal.valueOf(clampPercentage(nuevoValor)).setScale(2, RoundingMode.HALF_UP);
    }*/

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

    private double clampPercentage(double value) {
        return Math.max(0.0, Math.min(100.0, value));
    }

    private BigDecimal normalizeDomainScale(BigDecimal rawDomain) {
        if (rawDomain == null) {
            return BigDecimal.ZERO;
        }
        if (rawDomain.compareTo(BigDecimal.ONE) <= 0) {
            return rawDomain.multiply(BigDecimal.valueOf(100)).setScale(2, java.math.RoundingMode.HALF_UP);
        }
        if (rawDomain.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        if (rawDomain.compareTo(BigDecimal.valueOf(100)) > 0) {
            return BigDecimal.valueOf(100);
        }
        return rawDomain.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    private Difficulties mapDifficultyByDomain(BigDecimal domain) {
        if (domain.compareTo(new BigDecimal("30.00")) < 0) {
            return Difficulties.EASY;
        }
        if (domain.compareTo(new BigDecimal("60.00")) < 0) {
            return Difficulties.MEDIUM;
        }
        return Difficulties.HARD;
    }

    private BigDecimal incrementByDifficulty(Difficulties difficulty) {
        if (difficulty == null) {
            return new BigDecimal("7.00");
        }
        return switch (difficulty) {
            case EASY -> new BigDecimal("7.00");
            case MEDIUM -> new BigDecimal("10.00");
            case HARD -> new BigDecimal("15.00");
        };
    }

    private BigDecimal calculateDeltaFromTest(BigDecimal testDomain) {
        BigDecimal minChange = new BigDecimal("-5.00"); // peor caso
        BigDecimal maxChange = new BigDecimal("15.00"); // mejor caso
        BigDecimal range = maxChange.subtract(minChange); // 20

        BigDecimal proportion = testDomain.divide(new BigDecimal("100.00"), 4, RoundingMode.HALF_UP);

        BigDecimal delta = minChange.add(proportion.multiply(range));

        return delta.setScale(2, RoundingMode.HALF_UP);
    }

}
