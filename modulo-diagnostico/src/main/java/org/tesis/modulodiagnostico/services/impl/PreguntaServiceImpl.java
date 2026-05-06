package org.tesis.modulodiagnostico.services.impl;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.CuestionarioPreguntaResponse;
import org.tesis.modulodiagnostico.mappers.PreguntaMapper;
import org.tesis.modulodiagnostico.repositories.IPreguntaRepository;
import org.tesis.modulodiagnostico.services.IPreguntaService;

import java.util.List;

@Service
public class PreguntaServiceImpl  implements IPreguntaService {

    private final IPreguntaRepository preguntaRepository;
    private final PreguntaMapper preguntaMapper;
    public PreguntaServiceImpl(IPreguntaRepository preguntaRepository, PreguntaMapper preguntaMapper) {
        this.preguntaRepository = preguntaRepository;
        this.preguntaMapper = preguntaMapper;
    }

    @Override
    public List<CuestionarioPreguntaResponse> getAllPreguntas() {
        return preguntaMapper.toCuestionarioPreguntaResponseList(preguntaRepository.findAll());
    }
}
