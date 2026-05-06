package org.tesis.gestorgrafo.services;

import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.dtos.response.CompetenciaResponse;
import org.tesis.gestorgrafo.models.Competencia;

import java.util.List;

public interface ICompetenciaService {
    Competencia saveCompetencia(CompetenciaDTO competenciaDTO);

    CompetenciaResponse getCompetenciaById(Long id);



}
