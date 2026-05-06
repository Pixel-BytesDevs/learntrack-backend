package com.tesis.authserver.controller;

import com.tesis.authserver.dto.CreateClientDto;
import com.tesis.authserver.dto.MessageDto;
import com.tesis.authserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@RequestBody CreateClientDto dto){
        log.info("sdasdasd");
        dto.getAuthorizationGrantTypes().stream().forEach(grant -> {
            log.info("sdsad -> {}", grant);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(dto));

    }
}
