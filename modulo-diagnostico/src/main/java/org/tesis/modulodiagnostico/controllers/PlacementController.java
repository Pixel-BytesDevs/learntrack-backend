package org.tesis.modulodiagnostico.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.tesis.modulodiagnostico.dtos.response.PlacementQuestionWithIdResponse;
import org.tesis.modulodiagnostico.dtos.response.PlacementTestResponse;
import org.tesis.modulodiagnostico.dtos.response.ResultLevelTestResponse;
import org.tesis.modulodiagnostico.models.placementtests.PlacementTest;
import org.tesis.modulodiagnostico.services.impl.placement.PlacementTestServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/placement")
@Tag(name= "Cuestionario de nivel de Aprendizaje", description = "Endpoints para gestionar el cuestionario de nivel de aprendizaje")
public class PlacementController {

    private final PlacementTestServiceImpl placementTestService;

    public PlacementController( PlacementTestServiceImpl placementTestServiceImpl) {
        this.placementTestService = placementTestServiceImpl;
    }

    @GetMapping
    public List<PlacementQuestionWithIdResponse> getPlacement() {
        return this.placementTestService.createPlacementTestForStudent();
    }

    @PostMapping
    @Operation(summary = "Iniciar cuestionario de nivel de aprendizaje", description = "Inicia un nuevo cuestionario de nivel de aprendizaje para el estudiante con el ID proporcionado.")
    public PlacementTestResponse createPlacement(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return this.placementTestService.startPlacementTest(userId);
    }

    /*@PutMapping("/{id}/submit")
    public PlacementTestResponse submitPlacementTest(@PathVariable(name = "id") Long id, @RequestBody PlacementTestResponse placementTest) {
        return this.placementTestService.submitPlacementTest(id, placementTest);
    }*/

    @PutMapping("/submit")
    public ResultLevelTestResponse submitPlacementTest(
            @RequestBody PlacementTestResponse placementTest,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("id");
        return this.placementTestService.submitPlacementTest(userId, placementTest);
    }

}