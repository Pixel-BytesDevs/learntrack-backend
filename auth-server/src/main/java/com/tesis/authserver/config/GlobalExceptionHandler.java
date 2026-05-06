package com.tesis.authserver.config;

import com.tesis.authserver.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<MessageDto> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new MessageDto(ex.getReason()));
    }
}