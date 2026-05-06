package org.tesis.gestorgrafo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tesis.gestorgrafo.dtos.CompetenciaDTO;
import org.tesis.gestorgrafo.dtos.response.CompetenciaResponse;
import org.tesis.gestorgrafo.dtos.response.TemaResponse;
import org.tesis.gestorgrafo.models.Competencia;
import org.tesis.gestorgrafo.dtos.base.ResponseAPI;
import org.tesis.gestorgrafo.services.ICompetenciaService;
import org.tesis.gestorgrafo.services.IGeminiService;
import org.tesis.gestorgrafo.services.IGrafoService;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://18.118.137.168/")
public class CompetenciaController {

    private final IGeminiService service;
    private final IGrafoService grafoService;
    private final ICompetenciaService competenciaService;

    public CompetenciaController(IGeminiService service, IGrafoService grafoService, ICompetenciaService competenciaService) {
        this.service = service;
        this.grafoService = grafoService;
        this.competenciaService = competenciaService;
    }


    @PostMapping("/nodos")
    public ResponseAPI<CompetenciaDTO> nodos(@RequestBody String documentoUrl) {
        return service.getCompentecia(documentoUrl);
    }

    @PostMapping("/generate-grafo")
    public ResponseEntity<Competencia> generateGrafo(@RequestBody String documentoUrl) {
        Competencia competencia = this.grafoService.generateGraph(documentoUrl);
        return ResponseEntity.ok(competencia);
    }

    @GetMapping("/grafo")
    public ResponseEntity<CompetenciaResponse> obtenerCompetencia() {
        return ResponseEntity.ok(this.competenciaService.getCompetenciaById(0L));
    }

    @GetMapping("/grafo-5")
    public ResponseEntity<List<TemaResponse>> obtenerCompetenciaCon5Primeros() {
        return ResponseEntity.ok(this.competenciaService.getCompetenciaById(0L).getTemas().subList(0,5));
    }


}
