package org.tesis.modulodiagnostico.config.batchs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.extras.QuestionSaveResult;
import org.tesis.modulodiagnostico.dtos.request.PlacementQuestionAndAlternativeRequest;
import org.tesis.modulodiagnostico.dtos.request.TopicRequest;
import org.tesis.modulodiagnostico.services.impl.placement.PlacementTestServiceImpl;
import org.tesis.modulodiagnostico.services.impl.placement.TopicServiceImpl;

import java.util.List;

@Service
public class GeminiBatchService {

    private static final int MAX_RETRIES = 3;
    private static final long DELAY_BETWEEN_REQUESTS_MS = 6000;
    private static final Logger log = LoggerFactory.getLogger(GeminiBatchService.class);

    private final PlacementTestServiceImpl placementTestService;
    private final TopicServiceImpl topicService;

    public GeminiBatchService(PlacementTestServiceImpl placementTestService, TopicServiceImpl topicService) {
        this.placementTestService = placementTestService;
        this.topicService = topicService;
    }

    public void batchData() {
        List<TopicRequest> topics = topicService.getAll();


        for (TopicRequest topic : topics.subList(31,32)) {
            PlacementQuestionAndAlternativeRequest request = new PlacementQuestionAndAlternativeRequest();
            request.setTopic(topic);
            request.setGrade(5);

            try {
                log.info("GENERANDO PREGUNTAS PARA TEMA: {}", topic.getTopic());
                List<QuestionSaveResult> results = placementTestService.fillQuestionAndAlernativesFromGemini(request);
                log.info("LLEGO");
                long successCount = results.stream().filter(QuestionSaveResult::isSuccess).count();
                long failedCount = results.size() - successCount;

                log.info("Gemini batch completo para tema '{}' completado: {} guardadas, {} fallidas: ", topic.getTopic(), successCount, failedCount);
            } catch (Exception e) {
                log.error("Error al generar preguntas para '{}': {}", topic.getTopic(), e.getMessage());
            }

            try {
                Thread.sleep(DELAY_BETWEEN_REQUESTS_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("Proceso de generación completado para todos los temas.");
        }
    }
}
