package org.tesis.modulodiagnostico.services.impl.placement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.request.TopicRequest;
import org.tesis.modulodiagnostico.exceptions.ExternalServiceException;
import org.tesis.modulodiagnostico.mappers.TopicMapper;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.repositories.TopicRepository;
import org.tesis.modulodiagnostico.services.clients.GraphClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);
    private final GraphClient graphClient;
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(GraphClient graphClient, TopicRepository topicRepository, TopicMapper topicMapper) {
        this.graphClient = graphClient;
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    public Topic getById(String id) {
        return topicRepository.findById(id).orElse(null);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public List<Topic> getPrincipalTopics() {
        return topicRepository.findByIsPrincipalTrue();
    }

    public List<Topic> getBasicTopics() {
        return topicRepository.findByIsBasicTrue();
    }

    public List<Topic> getAdvancedTopics() {
        return topicRepository.findByIsAdvancedTrue();
    }

    /**
     * Obtiene los tópicos desde el servicio de grafo (fuente de verdad).
     * Si el servicio externo no está disponible, usa los tópicos persistidos localmente.
     */
    public List<Topic> getAllTopicsFromGraph() {
        try {
            List<TopicRequest> graphTopics = graphClient.getAllTopics();
            if (graphTopics == null || graphTopics.isEmpty()) {
                return topicRepository.findAll();
            }

            List<Topic> topics = new ArrayList<>();
            graphTopics.forEach(t -> {
                if (t == null || t.getTopicId() == null) {
                    return;
                }
                Topic topic = topicRepository.findById(t.getTopicId()).orElse(null);
                if (topic != null) {
                    topics.add(topic);
                }
            });
            return topics.isEmpty() ? topicRepository.findAll() : topics;
        } catch (ExternalServiceException ex) {
            log.warn("Servicio de grafo no disponible. Usando tópicos de la base local.");
            return topicRepository.findAll();
        }
    }

    public List<TopicRequest> getAll() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map(topicMapper::toTopicRequest).toList();
    }

    public List<Topic> getRandomTopics() {
        List<Topic> allTopics = topicRepository.findAll();
        Collections.shuffle(allTopics);
        return allTopics.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Topic> getFirstFiveTopics() {
        //List<TopicGraphDTO> topics = graphClient.getFirstFiveTopics();
        List<Topic> fiveTopics = topicRepository.findAll().subList(0,5);
//        topics.forEach(
//                tema -> {
//                    Topic topic = topicRepository.findById(tema.getTemaId()).orElse(null);
//                    fiveTopics.add(topic);
//                }
//        );
        return fiveTopics;
    }

    public String synchronizeTopics() {
        log.info("SINCRONIZANDO TEMAS DESDE EL SERVICIO DE GRAFO...");
        try {
            List<TopicRequest> topics = graphClient.getAllTopics();
            log.info("TOTAL DE TEMAS OBTENIDOS: {}", topics.size());

            if (topics.isEmpty()) {
                log.warn("LA LISTA DE TEMAS RECIBIDA ESTA VACÍA. SE CANCELA LA SINCRONIZACIÓN.");
                return "No se encontraron temas para sincronizar.";
            }

            log.info("BORRANDO TEMAS EXISTENTES EN LA BASE DE DATOS...");
            topicRepository.deleteAll();

            topicRepository.saveAll(
                    topics.stream().map(topicMapper::toTopicEntity).toList()
            );

            log.info("TEMAS SINCRONIZADOS EXITOSAMENTE ({} temas).", topics.size());
            return "Synchronized " + topics.size() + " topics.";

        } catch (ExternalServiceException ex) {
            log.error("Error al sincronizar temas desde el servicio de grafo: {}", ex.getMessage(), ex);
            return "Error: no se pudo sincronizar temas (servicio de grafo no disponible)";
        } catch (Exception ex) {
            log.error("Error inesperado durante la sincronización de temas", ex);
            return "Error inesperado durante la sincronización.";
        }
    }
}
