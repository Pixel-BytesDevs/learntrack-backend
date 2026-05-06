package org.tesis.modulodiagnostico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesis.modulodiagnostico.dtos.CuestionarioAlternativaResponse;
import org.tesis.modulodiagnostico.dtos.CuestionarioPreguntaResponse;
import org.tesis.modulodiagnostico.dtos.CuestionarioResponse;
import org.tesis.modulodiagnostico.services.IAlternativaService;
import org.tesis.modulodiagnostico.services.ICuestionarioService;
import org.tesis.modulodiagnostico.services.IPreguntaService;

import java.util.List;

@RestController
public class CuestionarioController {
    private final ICuestionarioService cuestionarioService;
    private final IPreguntaService preguntaService;
    private final IAlternativaService alternativaService;

    @Autowired
    public CuestionarioController(ICuestionarioService cuestionarioService, IPreguntaService preguntaService, IAlternativaService alternativaService) {
        this.cuestionarioService = cuestionarioService;
        this.preguntaService = preguntaService;
        this.alternativaService = alternativaService;
    }

    @GetMapping("/cuestionario")
    public CuestionarioResponse getCuestionario() {
        return cuestionarioService.getCuestionarioEstilo();
    }

    @GetMapping("/preguntas")
    public List<CuestionarioPreguntaResponse> getAllPreguntas() {
        return preguntaService.getAllPreguntas();
    }

    @GetMapping("/alternativas")
    public List<CuestionarioAlternativaResponse> getAllAlternativas() {
        return alternativaService.getAllAlternativas();
    }
}
