package org.tesis.modulodiagnostico.services;

import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioRequest;
import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioResponse;


public interface IUsuariosCuestionarioService {
    CuestionarioEnvioResponse guardarCuestionario(CuestionarioEnvioRequest request, boolean reemplazarPrevio);
}
