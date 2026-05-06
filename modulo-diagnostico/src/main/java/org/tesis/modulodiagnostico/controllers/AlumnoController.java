package org.tesis.modulodiagnostico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tesis.modulodiagnostico.dtos.request.AlumnoDataRequest;
import org.tesis.modulodiagnostico.services.impl.AlumnoService;
import org.tesis.modulodiagnostico.services.impl.placement.TopicServiceImpl;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/create")
    public ResponseEntity<String> createAlumno(@RequestBody AlumnoDataRequest request) {

        String response = alumnoService.createAlumno(request);

        return ResponseEntity.ok(response);
    }
}