package org.tesis.modulodiagnostico.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tesis.modulodiagnostico.config.batchs.GeminiBatchService;

@RestController
@RequestMapping("/batch/gemini")
public class GeminiBatchController {

    private final GeminiBatchService batchService;

    public GeminiBatchController(GeminiBatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runGeminiBatch() {
        batchService.batchData();
        return ResponseEntity.ok("Gemini batch executed successfully.");
    }


}
