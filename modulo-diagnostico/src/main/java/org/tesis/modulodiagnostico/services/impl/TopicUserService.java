package org.tesis.modulodiagnostico.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tesis.modulodiagnostico.dtos.request.TopicRequest;
import org.tesis.modulodiagnostico.dtos.response.ActiveTopicsResponse;
import org.tesis.modulodiagnostico.dtos.response.ProgressGraph;
import org.tesis.modulodiagnostico.dtos.response.TopicPending;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.usuarios.TopicUser;
import org.tesis.modulodiagnostico.repositories.TopicUserRepository;
import org.tesis.modulodiagnostico.services.clients.GraphClient;
import org.tesis.modulodiagnostico.services.impl.placement.TopicServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TopicUserService {
    private static final Logger log = LoggerFactory.getLogger(TopicUserService.class);
    private final TopicUserRepository topicUserRepository;
    private final TopicServiceImpl topicService;
    private final UserServiceImpl userService;
    private final GraphClient graphClient;

    public TopicUserService(TopicUserRepository topicUserRepository, TopicServiceImpl topicService, UserServiceImpl userService, GraphClient graphClient) {
        this.userService = userService;
        this.topicUserRepository = topicUserRepository;
        this.topicService = topicService;
        this.graphClient = graphClient;
    }

    // Método para obtener los temas (nodos) que no estan aun dominados por el usuario
    public List<TopicPending> getAllTopicForDomainByUserId(Long userId) {
        List<TopicUser> topicUsers = topicUserRepository.findAllByIdUsuario(userId);
        List<TopicPending> topicPendings = new ArrayList<>();
        topicUsers.forEach(
                tu -> {
                    if (tu.getDomainLevel().compareTo(new BigDecimal("80.00")) < 0 && tu.getActive()) {
                        TopicPending tp = new TopicPending();
                        tp.setTopicId(tu.getTopic().getId());
                        tp.setTopicName(tu.getTopic().getName());
                        tp.setDomainLevel(tu.getDomainLevel());
                        topicPendings.add(tp);
                    }
                }
        );
        return topicPendings.stream().sorted(
                (tp1, tp2) -> tp1.getTopicId().compareTo(tp2.getTopicId())
        ).toList(
        );
    }

    public TopicUser save(TopicUser topicUser) {
        return topicUserRepository.save(topicUser);
    }

    public List<ProgressGraph> findAllByUserId(Long userId) {
        List<TopicUser> topicUsers = topicUserRepository.findAllByIdUsuario(userId);
        List<ProgressGraph> progressGraphs = new ArrayList<>();
        topicUsers.forEach(
                tu -> {
                    ProgressGraph pg = new ProgressGraph();
                    pg.setId(tu.getTopic().getId());
                    pg.setLabel(tu.getTopic().getName());
                    pg.setDominio(tu.getDomainLevel());
                    pg.setActive(tu.getActive());
                    List<String> conexiones = graphClient.getPostrequisitesByTopicId(tu.getTopic().getId()).stream().map(TopicRequest::getTopicId).toList();
                    pg.setConexiones(conexiones);
                    progressGraphs.add(pg);
                }
        );
        return progressGraphs.stream().sorted(
                (pg1, pg2) -> pg1.getId().compareTo(pg2.getId())
        ).toList(
        );
    }

    // Para un nodo nuevo
    @Transactional
    public TopicUser setInitialDomainInANodeByUser(String topicId, Long userId, BigDecimal domain) {
        if (!allRequiredTopicsAreActiveAndHighDomainLevel(topicId, userId)) {
            return null;
        }
        BigDecimal normalizedDomain = normalizeDomainScale(domain);
        TopicUser topicUser = topicUserRepository.findByTopicIdAndUserId(topicId, userId);
        topicUser.setTopic(topicService.getById(topicId));
        topicUser.setCreatedAt(LocalDateTime.now()); // -> Fecha de creación actual, agregar condicional cuando no tiene valor ya que indica que es la primera vez que se asigna un nivel de dominio
        topicUser.setActive(true);
        //topicUser.setUsuario(userService.findById(userId));
        topicUser.setIdUsuario(userId);
        topicUser.setDomainLevel(normalizedDomain);
        TopicUser saved = topicUserRepository.save(topicUser);
        if (normalizedDomain.compareTo(new BigDecimal("80.00")) >= 0) {
            openNewNodesForUser(topicId, userId);
        }
        return saved;
    }

    /**
     * Define la posición inicial del estudiante según el promedio del test inicial (0..12),
     * activando solo los nodos objetivo y bloqueando los demás.
     */
    @Transactional
    public void initializePositionAfterInitialTest(Long userId, BigDecimal avg012) {
        if (avg012 == null) {
            avg012 = BigDecimal.ZERO;
        }
        BigDecimal avg = avg012.setScale(2, RoundingMode.HALF_UP);

        List<TopicUser> topicUsers = topicUserRepository.findAllByIdUsuario(userId);
        if (topicUsers == null || topicUsers.isEmpty()) {
            initializeTopicUserForAnUser(userId);
            topicUsers = topicUserRepository.findAllByIdUsuario(userId);
        }

        boolean useBasic = avg.compareTo(BigDecimal.valueOf(8)) <= 0;
        BigDecimal initialDomain;

        // Reglas:
        // 0 -> basic @0
        // (0,4) -> basic @10
        // 4 -> basic @20
        // (4,8) -> basic @50
        // 8 -> basic @60
        // (8,12) -> principal @20
        // 12 -> principal @65
        if (avg.compareTo(BigDecimal.ZERO) == 0) {
            initialDomain = BigDecimal.ZERO;
            useBasic = true;
        } else if (avg.compareTo(BigDecimal.valueOf(4)) < 0) {
            initialDomain = BigDecimal.valueOf(10);
            useBasic = true;
        } else if (avg.compareTo(BigDecimal.valueOf(4)) == 0) {
            initialDomain = BigDecimal.valueOf(20);
            useBasic = true;
        } else if (avg.compareTo(BigDecimal.valueOf(8)) < 0) {
            initialDomain = BigDecimal.valueOf(50);
            useBasic = true;
        } else if (avg.compareTo(BigDecimal.valueOf(8)) == 0) {
            initialDomain = BigDecimal.valueOf(60);
            useBasic = true;
        } else if (avg.compareTo(BigDecimal.valueOf(12)) < 0) {
            initialDomain = BigDecimal.valueOf(20);
            useBasic = false; // principal
        } else {
            initialDomain = BigDecimal.valueOf(65);
            useBasic = false; // principal
        }

        for (TopicUser tu : topicUsers) {
            Topic topic = tu.getTopic();
            boolean target = useBasic
                    ? Boolean.TRUE.equals(topic.getIsBasic())
                    : Boolean.TRUE.equals(topic.getIsPrincipal());

            tu.setActive(target);
            tu.setUpdatedAt(LocalDateTime.now());
            tu.setDomainLevel(target ? initialDomain.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            topicUserRepository.save(tu);
        }
    }

    /**
     * Incrementa el dominio de un tópico en % y aplica clamp 0..100.
     */
    @Transactional
    public BigDecimal incrementDomain(Long userId, String topicId, BigDecimal incrementPercent) {
        TopicUser topicUser = topicUserRepository.findByTopicIdAndUserId(topicId, userId);
        BigDecimal current = normalizeDomainScale(topicUser.getDomainLevel());
        BigDecimal inc = incrementPercent == null ? BigDecimal.ZERO : incrementPercent;
        BigDecimal next = current.add(inc);
        if (next.compareTo(BigDecimal.valueOf(100)) > 0) {
            next = BigDecimal.valueOf(100);
        }
        topicUser.setDomainLevel(next.setScale(2, RoundingMode.HALF_UP));
        topicUser.setUpdatedAt(LocalDateTime.now());
        topicUserRepository.save(topicUser);
        if (next.compareTo(new BigDecimal("80.00")) >= 0) {
            openNewNodesForUser(topicId, userId);
        }
        return next;
    }


    /**
     * Método para inicializar los TopicUser (temas) de un usuario nuevo
     *
     * @param userId ID del usuario nuevo
     * @return Mensaje de confirmación
     * @description Aquí se asignan todos los temas existentes en la base de datos al usuario nuevo con un nivel de dominio inicial
     */
    public String initializeTopicUserForAnUser(Long userId) {

        /*if (!userService.existsUserById(userId)) {
            throw new UserNotFoundException();
        }*/

        //Usuario user = userService.findById(userId);

        List<Topic> topics = topicService.getAllTopics();

        IntStream.range(0, topics.size()).forEach(
                i -> {
                    Topic topic = topics.get(i);
                    TopicUser topicUser = new TopicUser();
                    topicUser.setTopic(topic);
                    topicUser.setActive(i == 0);
                    //topicUser.setUsuario(userService.findById(userId));
                    topicUser.setIdUsuario(userId);
                    topicUser.setDomainLevel(BigDecimal.ZERO);
                    //topicUser.setUsuario(user);
                    topicUserRepository.save(topicUser);
                }
        );

        return "Temas inicializados para el usuario con ID: " + userId;
    }

    private Boolean allRequiredTopicsAreActiveAndHighDomainLevel(String topicId, Long userId) {
        log.info("INICIO DE VERIFICACIÓN DE TEMAS REQUERIDOS PARA EL TEMA CON ID: {} Y USUARIO CON ID: {}", topicId, userId);
        // Lógica para verificar si todos los temas requeridos están activos y tienen un alto nivel de dominio
        List<TopicRequest> requiredTopics = graphClient.getPrerequisitesByTopicId(topicId);

        if (requiredTopics.isEmpty()) {
            return true; // No hay temas requeridos, se puede activar directamente
        }

        List<TopicUser> topicUsers = new ArrayList<>();

        requiredTopics.forEach(
                reqTopic -> {
                    log.info("VERIFICANDO EL TEMA REQUERIDO CON ID: {} PARA LA CANTIDAD: {}", reqTopic.getTopicId(), topicUserRepository.findByUserAndTopic(userId, reqTopic.getTopicId()).size());
                    TopicUser topicUser = topicUserRepository.findByUserAndTopic(userId, reqTopic.getTopicId()).getFirst();
                    if (topicUser != null) {
                        topicUsers.add(topicUser);
                    }
                }
        );

        int numTotal = topicUsers.stream().filter(p -> p.getActive().equals(true) && p.getDomainLevel().compareTo(new BigDecimal("80.00")) >= 0).toList().size();
        return requiredTopics.size() == numTotal;
    }

    private void openNewNodesForUser(String topicId, Long userId) {
        log.info("INICIO DE APERTURA DE NUEVOS NODOS PARA EL TEMA CON ID: {} Y USUARIO CON ID: {}", topicId, userId);
        List<TopicRequest> postrequisiteTopics = graphClient.getPostrequisitesByTopicId(topicId);
        if (postrequisiteTopics.isEmpty()) {
            log.info("NO HAY TEMAS POSTREQUISITOS PARA EL TEMA CON ID: {}", topicId);
            return;
        }
        postrequisiteTopics.forEach(
                postTopic -> {
                    TopicUser topicUser = topicUserRepository.findByUserAndTopic(userId, postTopic.getTopicId()).getFirst();
                    if (topicUser != null && !topicUser.getActive()) {
                        if (allRequiredTopicsAreActiveAndHighDomainLevel(postTopic.getTopicId(), userId)) {
                            log.info("ACTIVANDO EL TEMA CON ID: {} PARA EL USUARIO CON ID: {}", postTopic.getTopicId(), userId);
                            topicUser.setActive(true);
                            topicUserRepository.save(topicUser);
                        }
                    }
                }
        );
    }

    /**
     * Normaliza valores de dominio para trabajar SIEMPRE en escala 0..100.
     * Si llega en 0..1 (legacy), se convierte multiplicando por 100.
     */
    private BigDecimal normalizeDomainScale(BigDecimal rawDomain) {
        if (rawDomain == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal normalized = rawDomain.compareTo(BigDecimal.ONE) <= 0
                ? rawDomain.multiply(BigDecimal.valueOf(100)).setScale(2, java.math.RoundingMode.HALF_UP)
                : rawDomain.setScale(2, java.math.RoundingMode.HALF_UP);

        if (normalized.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        if (normalized.compareTo(BigDecimal.valueOf(100)) > 0) {
            return BigDecimal.valueOf(100);
        }
        return normalized;
    }

    public List<ActiveTopicsResponse> getActiveTopicsByUserId(Long userId) {
        List<TopicUser> topicUsers = topicUserRepository.findAllByIdUsuario(userId);

        return topicUsers.stream()
                .filter(TopicUser::getActive) // solo activos
                .map(tu -> new ActiveTopicsResponse(
                        tu.getTopic().getId(),
                        tu.getTopic().getName(),
                        tu.getDomainLevel()
                ))
                .sorted((a, b) -> a.getId().compareTo(b.getId()))
                .toList();
    }
}
