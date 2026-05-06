package org.tesis.modulodiagnostico.services;

import org.tesis.modulodiagnostico.dtos.CuestionarioPreguntaResponse;
import org.tesis.modulodiagnostico.models.cuestionariovark.Pregunta;

import java.util.List;

public interface IPreguntaService {

    List<CuestionarioPreguntaResponse> getAllPreguntas();
}
