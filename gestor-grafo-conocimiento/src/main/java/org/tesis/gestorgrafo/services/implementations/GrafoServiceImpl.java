package org.tesis.gestorgrafo.services.implementations;

import org.springframework.stereotype.Service;
import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.models.Competencia;
import org.tesis.gestorgrafo.dtos.base.ResponseAPI;
import org.tesis.gestorgrafo.services.ICompetenciaService;
import org.tesis.gestorgrafo.services.IGeminiService;
import org.tesis.gestorgrafo.services.IGrafoService;

@Service
public class GrafoServiceImpl implements IGrafoService {


    private final IGeminiService geminiService;
    private final ICompetenciaService competenciaService;

    public GrafoServiceImpl(IGeminiService geminiService, ICompetenciaService competenciaService) {
        this.geminiService = geminiService;
        this.competenciaService = competenciaService;
    }

    @Override
    public Competencia generateGraph(String url) {
        ResponseAPI<CompetenciaDTO> response = this.geminiService.getCompentecia(url);
        CompetenciaDTO data = response.getData();
        Competencia competencia = this.competenciaService.saveCompetencia(data);
        return competencia;
    }
}
