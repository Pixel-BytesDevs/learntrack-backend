package org.tesis.gestorgrafo.controllers.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tesis.gestorgrafo.core.errors.ErrorApp;
import org.tesis.gestorgrafo.dtos.base.ErrorResponse;
import org.tesis.gestorgrafo.exceptions.CompetenciaNotFoundException;

@RestControllerAdvice
public class GlobalController {


    @ExceptionHandler(CompetenciaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCompentenciaNotFoundException() {
        ErrorResponse error = new ErrorResponse();
        error.setCode(ErrorApp.COMPETENCIA_NOT_FOUND.getCode());
        error.setMessage(ErrorApp.COMPETENCIA_NOT_FOUND.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
