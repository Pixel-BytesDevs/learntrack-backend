package org.tesis.modulodiagnostico.services.impl.recommendations;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.extras.MessageRecommendation;
import org.tesis.modulodiagnostico.dtos.response.LearningStyleResponse;
import org.tesis.modulodiagnostico.dtos.response.PrerecommendationData;
import org.tesis.modulodiagnostico.dtos.response.TopicPending;
import org.tesis.modulodiagnostico.exceptions.UserNotFoundException;
import org.tesis.modulodiagnostico.services.clients.OAClient;
import org.tesis.modulodiagnostico.services.clients.RecommedationClient;
import org.tesis.modulodiagnostico.services.impl.EstiloAprendizajeServiceImpl;
import org.tesis.modulodiagnostico.services.impl.TopicUserService;
import org.tesis.modulodiagnostico.services.impl.UserServiceImpl;

import java.util.List;

@Service
public class RecommendationService {

    private final OAClient oaclient;
    private final TopicUserService topicUserService;
    private final UserServiceImpl userServiceImpl;
    private final EstiloAprendizajeServiceImpl estiloAprendizajeService;
    private final RecommedationClient recommedationClient;

    public RecommendationService(OAClient oaclient, TopicUserService topicUserService, UserServiceImpl userServiceImpl, EstiloAprendizajeServiceImpl estiloAprendizajeService, RecommedationClient recommedationClient) {
        this.recommedationClient = recommedationClient;
        this.oaclient = oaclient;
        this.estiloAprendizajeService = estiloAprendizajeService;
        this.userServiceImpl = userServiceImpl;
        this.topicUserService = topicUserService;
    }

    public MessageRecommendation initRecommendation(Long userId) {
        PrerecommendationData data = new PrerecommendationData();
        /*if(!userServiceImpl.existsUserById(userId)) {
            throw new UserNotFoundException();
        }*/
       // Usuario user = userServiceImpl.findById(userId);
        //data.setUserId(user.getId());
        data.setUserId(userId);
        List<TopicPending> topicsToDomain = topicUserService.getAllTopicForDomainByUserId(userId);
        data.setTopicPendings(topicsToDomain);
        List<LearningStyleResponse> styles = estiloAprendizajeService.getAllLearningStylesByUserId(userId);
        data.setLearningStyleResponses(styles);

        // TODO: 'data' sera enviado al motor de recomendaciones, faltaria llamar aca al cliente motor
        MessageRecommendation result = recommedationClient.initRecommendation(data);
        if(result == null) {
            // manejar el caso cuando no se recibe respuesta del motor de recomendaciones
            // por ejemplo, lanzar una excepcion o retornar un mensaje por defecto
            throw new RuntimeException("No se pudo obtener recomendacion del motor");
        }
        // por ahora solo se retorna la data recopilada
        return result;
    }

    public void finishRecommendations(Long userId) {

        MessageRecommendation result = recommedationClient.finishRecommendations(userId);

        if (result == null) {
            throw new RuntimeException("No se pudo finalizar las recomendaciones");
        }
    }

    private void sendNodesToRecommendationEngine() {

    }
}
