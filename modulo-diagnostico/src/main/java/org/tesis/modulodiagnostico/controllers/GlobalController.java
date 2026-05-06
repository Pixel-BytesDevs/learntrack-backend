package org.tesis.modulodiagnostico.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tesis.modulodiagnostico.dtos.ErrorResponse;
import org.tesis.modulodiagnostico.exceptions.ExternalServiceException;
import org.tesis.modulodiagnostico.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalController {

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException() {
        ErrorResponse error = new ErrorResponse();
        error.setCode("EXTERNAL_SERVICE_ERROR");
        error.setMessage("An error occurred while communicating with an external service.");
        return ResponseEntity.status(502).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException() {
        ErrorResponse error = new ErrorResponse();
        error.setCode("USER_NOT_FOUND");
        error.setMessage("The requested user was not found.");
        return ResponseEntity.status(404).body(error);
    }
}
