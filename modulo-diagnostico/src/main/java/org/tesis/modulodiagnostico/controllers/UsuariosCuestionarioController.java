package org.tesis.modulodiagnostico.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioRequest;
import org.tesis.modulodiagnostico.dtos.CuestionarioEnvioResponse;
import org.tesis.modulodiagnostico.dtos.EstiloDominioResponse;
import org.tesis.modulodiagnostico.services.IEstiloAprendizajeService;
import org.tesis.modulodiagnostico.services.IUsuariosCuestionarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cuestionarios")
public class UsuariosCuestionarioController {
    private final IUsuariosCuestionarioService service;
    private final IEstiloAprendizajeService estiloAprendizajeService;

    public UsuariosCuestionarioController(IUsuariosCuestionarioService service,
                                          IEstiloAprendizajeService estiloAprendizajeService) {
        this.service = service;
        this.estiloAprendizajeService = estiloAprendizajeService;
    }

    /**
     * Envía TODO el cuestionario (16 preguntas con sus alternativas marcadas).
     * @param reemplazar default=true: borra marcas previas del usuario para esas preguntas y guarda las nuevas.
     */
    @PostMapping("/respuestas")
    public ResponseEntity<CuestionarioEnvioResponse> enviarCuestionario(
            @Validated @RequestBody CuestionarioEnvioRequest body,
            @RequestParam(defaultValue = "true") boolean reemplazar) {

        CuestionarioEnvioResponse resp = service.guardarCuestionario(body, reemplazar);

        // Calcular y persistir porcentajes VARK
        List<EstiloDominioResponse> estilos = estiloAprendizajeService.recalcularYGuardar(body.getUsuarioId());
        resp.setEstilos(estilos);

        return ResponseEntity.created(URI.create("/cuestionarios/respuestas?usuarioId=" + resp.getUsuarioId()))
                .body(resp);
    }
}
