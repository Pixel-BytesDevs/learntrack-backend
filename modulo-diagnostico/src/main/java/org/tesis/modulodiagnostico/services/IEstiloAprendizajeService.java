package org.tesis.modulodiagnostico.services;

import org.tesis.modulodiagnostico.dtos.EstiloDominioResponse;

import java.util.List;

public interface IEstiloAprendizajeService {
    List<EstiloDominioResponse> recalcularYGuardar(Long usuarioId);
}
