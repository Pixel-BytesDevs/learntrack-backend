package org.tesis.modulodiagnostico.mappers;

import org.springframework.stereotype.Component;
import org.tesis.modulodiagnostico.dtos.response.DifficultyResponse;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;

@Component
public class DifficultyMapper {

    public DifficultyResponse mapToDifficultyResponse(Difficulty difficulty) {
        DifficultyResponse response = new DifficultyResponse();
        response.setId(difficulty.getId());
        response.setNombre(difficulty.getName().name());
        return response;
    }
}
