package org.tesis.gestorgrafo.services;

import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.dtos.base.ResponseAPI;

public interface IGeminiService {

    ResponseAPI<CompetenciaDTO> getCompentecia(String url);
}
