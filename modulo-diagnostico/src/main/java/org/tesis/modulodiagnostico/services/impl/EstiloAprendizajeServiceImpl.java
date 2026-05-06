package org.tesis.modulodiagnostico.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.EstiloDominioResponse;
import org.tesis.modulodiagnostico.dtos.response.LearningStyleResponse;
import org.tesis.modulodiagnostico.models.cuestionariovark.EstiloVark;
import org.tesis.modulodiagnostico.models.usuarios.EstiloAprendizaje;
import org.tesis.modulodiagnostico.repositories.IEstiloAprendizajeRepository;
import org.tesis.modulodiagnostico.repositories.IEstiloVarkRepository;
import org.tesis.modulodiagnostico.repositories.IUsuariosCuestionarioRepository;
import org.tesis.modulodiagnostico.services.IEstiloAprendizajeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstiloAprendizajeServiceImpl implements IEstiloAprendizajeService {

    private final IUsuariosCuestionarioRepository ucRepo;
    private final IEstiloAprendizajeRepository eaRepo;
    private final IEstiloVarkRepository estiloRepo;

    public EstiloAprendizajeServiceImpl(IUsuariosCuestionarioRepository ucRepo,
                                        IEstiloAprendizajeRepository eaRepo,
                                        IEstiloVarkRepository estiloRepo) {
        this.ucRepo = ucRepo;
        this.eaRepo = eaRepo;
        this.estiloRepo = estiloRepo;
    }

    @Override
    @Transactional
    public List<EstiloDominioResponse> recalcularYGuardar(Long usuarioId) {
        // Conteo por estilo desde DB
        List<Object[]> rows = ucRepo.countSeleccionesPorEstilo(usuarioId); // [estiloVarkId(Long), total(Long)]
        Map<Long, Long> conteos = rows.stream()
                .collect(Collectors.toMap(r -> (Long) r[0], r -> (Long) r[1]));

        long totalSelecciones = conteos.values().stream().mapToLong(Long::longValue).sum();

        // Trae los 4 estilos para garantizar 4 filas
        List<EstiloVark> estilos = estiloRepo.findAll();

        List<EstiloDominioResponse> resultado = new ArrayList<>();
        LocalDateTime ahora = LocalDateTime.now();

        for (EstiloVark e : estilos) {
            Long estiloId = e.getId();
            long count = conteos.getOrDefault(estiloId, 0L);

            BigDecimal porcentaje = (totalSelecciones == 0)
                    ? BigDecimal.ZERO
                    : BigDecimal.valueOf(count)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(totalSelecciones), 2, RoundingMode.HALF_UP);

            // UPSERT (usuario, estilo)
            EstiloAprendizaje ea = eaRepo.findByUsuarioIdAndEstiloVark_Id(usuarioId, estiloId)
                    .orElseGet(EstiloAprendizaje::new);

            /*ea.setUsuario(new org.tesis.modulodiagnostico.models.usuarios.Usuario(){{
                setId(usuarioId); // referencia por id (sin cargar todo el objeto)
            }});*/
            ea.setUsuarioId(usuarioId);
            ea.setEstiloVark(e);
            ea.setNivelPorcentaje(porcentaje);
            ea.setUpdatedAt(ahora);
            eaRepo.save(ea);

            resultado.add(new EstiloDominioResponse(estiloId, e.getTipo().name(), porcentaje));
        }

        return resultado;
    }

    public List<LearningStyleResponse> getAllLearningStylesByUserId(Long userId) {
        List<EstiloAprendizaje> estilos = eaRepo.findAllByUsuarioId(userId);
        return estilos.stream().map(
                ea -> new LearningStyleResponse(
                        ea.getEstiloVark().getTipo().name(),
                        ea.getNivelPorcentaje()
                )
        ).toList();
    }
}