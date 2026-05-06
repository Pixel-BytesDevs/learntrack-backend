package org.tesis.gestorgrafo.mappers;

import org.springframework.stereotype.Component;
import org.tesis.gestorgrafo.dtos.response.CompetenciaResponse;
import org.tesis.gestorgrafo.dtos.response.TemaResponse;
import org.tesis.gestorgrafo.dtos.response.TopicWithSimpleResponse;
import org.tesis.gestorgrafo.models.Competencia;
import org.tesis.gestorgrafo.models.Tema;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompetenciaMapper {

    public CompetenciaResponse toResponse(Competencia competencia) {
        List<TemaResponse> temaResponses = new ArrayList<>();
        CompetenciaResponse competenciaResponse = new CompetenciaResponse();
        competenciaResponse.setId(competencia.getId());
        competenciaResponse.setArea(competencia.getArea());
        competenciaResponse.setDescripcion(competencia.getDescripcion());
        competenciaResponse.setNivel(competencia.getNivel().name());
        competenciaResponse.setCurriculoRef(competencia.getCurriculoRef());

        competencia.getTemas().stream().map(this::toTemaResponse).forEach(temaResponses::add);
        competenciaResponse.setTemas(temaResponses);
        return competenciaResponse;
    }


    private TemaResponse toTemaResponse(Tema tema) {
        List<TemaResponse> temaPre = new ArrayList<>();
        TemaResponse temaResponse = new TemaResponse();
        temaResponse.setTemaId(tema.getTemaId());
        temaResponse.setNombre(tema.getNombre());
        temaResponse.setNivelDificultad(tema.getNivel_dificultad());
        temaResponse.setGradoRecomendado(tema.getGrado_recomendado());

        tema.getTemasRequeridos().stream().map(this::toPrerequisitos).forEach(temaPre::add);
        temaResponse.setTemasRequeridos(temaPre);
        return temaResponse;
    }

    private TemaResponse toPrerequisitos(Tema tema) {

        TemaResponse temaResponse = new TemaResponse();
        temaResponse.setTemaId(tema.getTemaId());
        temaResponse.setNombre(tema.getNombre());
        temaResponse.setNivelDificultad(tema.getNivel_dificultad());
        temaResponse.setGradoRecomendado(tema.getGrado_recomendado());

        return temaResponse;
    }



}
