package org.tesis.modulodiagnostico.services;

import org.tesis.modulodiagnostico.dtos.CuestionarioAlternativaResponse;
import org.tesis.modulodiagnostico.models.cuestionariovark.Alternativa;

import java.util.List;

public interface IAlternativaService {

    List<CuestionarioAlternativaResponse> getAllAlternativas();
}
