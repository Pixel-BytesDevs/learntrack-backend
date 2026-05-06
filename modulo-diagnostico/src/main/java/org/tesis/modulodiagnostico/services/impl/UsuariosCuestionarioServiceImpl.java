package org.tesis.modulodiagnostico.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioRequest;
import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioResponse;
import org.tesis.modulodiagnostico.exceptions.BadRequestException;
import org.tesis.modulodiagnostico.models.cuestionariovark.Alternativa;
import org.tesis.modulodiagnostico.models.cuestionariovark.Pregunta;
import org.tesis.modulodiagnostico.models.usuarios.UsuariosCuestionario;
import org.tesis.modulodiagnostico.repositories.IAlternativaRepository;
import org.tesis.modulodiagnostico.repositories.IPreguntaRepository;
import org.tesis.modulodiagnostico.repositories.IUsuariosCuestionarioRepository;
import org.tesis.modulodiagnostico.services.IUsuariosCuestionarioService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UsuariosCuestionarioServiceImpl implements IUsuariosCuestionarioService {
    private final IUsuariosCuestionarioRepository ucRepo;
    private final IAlternativaRepository alternativaRepo;
    private final IPreguntaRepository preguntaRepo;

    public UsuariosCuestionarioServiceImpl(IUsuariosCuestionarioRepository ucRepo,
                                           IAlternativaRepository alternativaRepo,
                                           IPreguntaRepository preguntaRepo
                                           ) {
        this.ucRepo = ucRepo;
        this.alternativaRepo = alternativaRepo;
        this.preguntaRepo = preguntaRepo;
    }

    @Override
    @Transactional
    public CuestionarioEnvioResponse guardarCuestionario(CuestionarioEnvioRequest request, boolean reemplazarPrevio) {

        if (request.getRespuestas() == null || request.getRespuestas().isEmpty()) {
            throw new BadRequestException("No hay respuestas en la solicitud.");
        }

        // Reunimos IDs de preguntas y alternativas
        Set<Long> preguntaIds = request.getRespuestas().stream()
                .map(CuestionarioEnvioRequest.RespuestaPreguntaRequest::getPreguntaId)
                .collect(Collectors.toSet());

        Set<Long> alternativaIds = request.getRespuestas().stream()
                .flatMap(r -> r.getAlternativaIds().stream())
                .collect(Collectors.toSet());

        // Validamos que existan todas las preguntas
        List<Pregunta> preguntas = preguntaRepo.findAllById(preguntaIds);
        if (preguntas.size() != preguntaIds.size()) {
            throw new BadRequestException("Alguna pregunta no existe. Enviadas: " + preguntaIds +
                    ", existentes: " + preguntas.stream().map(Pregunta::getId).collect(Collectors.toSet()));
        }
        Map<Long, Pregunta> preguntaMap = preguntas.stream()
                .collect(Collectors.toMap(Pregunta::getId, Function.identity()));

        // Validamos alternativas
        List<Alternativa> alternativas = alternativaRepo.findAllById(alternativaIds);
        if (alternativas.size() != alternativaIds.size()) {
            throw new BadRequestException("Alguna alternativa no existe. Enviadas: " + alternativaIds +
                    ", existentes: " + alternativas.stream().map(Alternativa::getId).collect(Collectors.toSet()));
        }
        Map<Long, Alternativa> alternativaMap = alternativas.stream()
                .collect(Collectors.toMap(Alternativa::getId, Function.identity()));

        // Verificamos consistencia: cada alternativa pertenece a su pregunta
        for (var r : request.getRespuestas()) {
            Long pid = r.getPreguntaId();
            for (Long aid : r.getAlternativaIds()) {
                Alternativa alt = alternativaMap.get(aid);
                if (alt.getPregunta() == null || !Objects.equals(alt.getPregunta().getId(), pid)) {
                    throw new BadRequestException("La alternativa " + aid + " no pertenece a la pregunta " + pid);
                }
            }
        }

        // Si se reemplaza, borra respuestas previas del usuario SOLO para estas preguntas
        if (reemplazarPrevio) {
            ucRepo.deleteByUsuarioAndPreguntas(request.getUsuarioId(), preguntaIds);
        }

        // Construimos entidades a insertar
        List<UsuariosCuestionario> aGuardar = new ArrayList<>();
        for (var r : request.getRespuestas()) {
            Pregunta p = preguntaMap.get(r.getPreguntaId());
            for (Long aid : r.getAlternativaIds()) {
                UsuariosCuestionario uc = new UsuariosCuestionario();
                uc.setUsuarioId(request.getUsuarioId());
                uc.setPregunta(p);
                uc.setAlternativa(alternativaMap.get(aid));
                aGuardar.add(uc);
            }
        }

        ucRepo.saveAll(aGuardar);

        CuestionarioEnvioResponse resp = new CuestionarioEnvioResponse();
        resp.setUsuarioId(request.getUsuarioId());
        resp.setPreguntasProcesadas(preguntaIds.size());
        resp.setRegistrosInsertados(aGuardar.size());
        resp.setMessage("Cuestionario registrado correctamente");
        resp.setDetalles(
                request.getRespuestas().stream()
                        .map(r -> new CuestionarioEnvioResponse.PreguntaResultado(r.getPreguntaId(), r.getAlternativaIds()))
                        .collect(Collectors.toList())
        );
        return resp;
    }
}
