package org.tesis.gestorgrafo.services.implementations;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.dtos.response.CompetenciaResponse;
import org.tesis.gestorgrafo.dtos.NodoDTO;
import org.tesis.gestorgrafo.exceptions.CompetenciaNotFoundException;
import org.tesis.gestorgrafo.mappers.CompetenciaMapper;
import org.tesis.gestorgrafo.models.Competencia;
import org.tesis.gestorgrafo.models.NivelCompetencia;
import org.tesis.gestorgrafo.models.Tema;
import org.tesis.gestorgrafo.repositories.CompetenciaRepository;
import org.tesis.gestorgrafo.repositories.TemaRepository;
import org.tesis.gestorgrafo.services.ICompetenciaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompetenciaServiceImpl implements ICompetenciaService {

    private final CompetenciaRepository competenciaRepository;
    private final TemaRepository temaRepository;
    private final CompetenciaMapper competenciaMapper;

    public CompetenciaServiceImpl(CompetenciaRepository competenciaRepository, TemaRepository temaRepository, CompetenciaMapper competenciaMapper) {
        this.competenciaRepository = competenciaRepository;
        this.temaRepository = temaRepository;
        this.competenciaMapper = competenciaMapper;
    }

    @Transactional
    @Override
    public Competencia saveCompetencia(CompetenciaDTO competenciaDTO) {
        // Mapeo de CompetenciaDTO a la entidad Competencia
        Competencia competencia = mapToCompetencia(competenciaDTO);


        // Guardamos la competencia en la base de datos
        Competencia savedCompetencia = competenciaRepository.save(competencia);

        // Mapeo de los nodos (temas) y luego los guardamos
        List<Tema> temas = competenciaDTO.getNodos().stream()
                .map(this::mapToTema)
                .collect(Collectors.toList());


        // Guardar todos los temas en la base de datos
        temaRepository.saveAll(temas);


        // Establecer relaciones de prerrequisitos entre los temas
        this.establecerRelacionesPrerrequisitos(competenciaDTO.getNodos());

        // Asociar los temas a la competencia
        savedCompetencia.setTemas(temas);

        // Guardar la competencia con los temas asociados
        return competenciaRepository.save(savedCompetencia);
    }

    @Override
    public CompetenciaResponse getCompetenciaById(Long id) {
        if(!this.competenciaRepository.existsById(id)) {
            throw new CompetenciaNotFoundException();
        }
        return this.competenciaMapper.toResponse(Objects.requireNonNull(competenciaRepository.findById(id).orElse(null)));
    }

    // TODO: Refactorizar metodos

    private Competencia mapToCompetencia(CompetenciaDTO competenciaDTO) {
        Competencia competencia = new Competencia();
        competencia.setDescripcion(competenciaDTO.getCompetenciaBase());
        competencia.setNivel(NivelCompetencia.valueOf(competenciaDTO.getNivel().toUpperCase())); // Mapear el nivel
        competencia.setArea("Álgebra");
        competencia.setCurriculoRef(competenciaDTO.getDocumentUrl());
        return competencia;
    }

    private Tema mapToTema(NodoDTO nodoDTO) {
        Tema tema = new Tema();
        tema.setTemaId(nodoDTO.getId());
        tema.setNombre(nodoDTO.getNombre());
        tema.setGrado_recomendado(nodoDTO.getGradoRecomendado());
        tema.setNivel_dificultad(nodoDTO.getNivelDificultad());
        return tema;
    }

    private void establecerRelacionesPrerrequisitos(List<NodoDTO> nodos) {
        // Cargar todos los temas actuales en un mapa en memoria
        Map<String, Tema> mapaTemas = temaRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Tema::getTemaId, t -> t));

        // Establecer relaciones
        for (NodoDTO nodo : nodos) {
            Tema temaActual = mapaTemas.get(nodo.getId());
            if (temaActual == null) continue;

            List<Tema> prerequisitos = new ArrayList<>();
            for (String preId : nodo.getPrerrequisitos()) {
                Tema prerequisito = mapaTemas.get(preId);
                if (prerequisito != null) prerequisitos.add(prerequisito);
            }

            temaActual.setTemasRequeridos(prerequisitos);
        }

        temaRepository.saveAll(mapaTemas.values()); // guarda todos con relaciones
    }
}