package com.tesis.authserver.service;

import com.tesis.authserver.entity.GlobalId;
import com.tesis.authserver.repository.GlobalIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GlobalIdService {

    private final GlobalIdRepository repository;

    public Integer getNextId() {
        GlobalId entity = repository.save(new GlobalId());
        return entity.getId();
    }
}