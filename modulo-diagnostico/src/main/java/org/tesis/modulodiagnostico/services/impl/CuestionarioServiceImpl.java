package org.tesis.modulodiagnostico.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.CuestionarioResponse;
import org.tesis.modulodiagnostico.mappers.PreguntaMapper;
import org.tesis.modulodiagnostico.repositories.IPreguntaRepository;
import org.tesis.modulodiagnostico.services.ICuestionarioService;

@Service
public class CuestionarioServiceImpl implements ICuestionarioService {

    private final IPreguntaRepository preguntaRepository;
    private final PreguntaMapper preguntaMapper;

    @Autowired
    public CuestionarioServiceImpl(IPreguntaRepository preguntaRepository, PreguntaMapper preguntaMapper) {
        this.preguntaRepository = preguntaRepository;
        this.preguntaMapper = preguntaMapper;
    }
    @Override
    public CuestionarioResponse getCuestionarioEstilo() {
        CuestionarioResponse cuestionarioResponse = new CuestionarioResponse();
        cuestionarioResponse.setPreguntas(preguntaMapper.toCuestionarioPreguntaResponseList(preguntaRepository.findAll()));
        return cuestionarioResponse;
    }
}
