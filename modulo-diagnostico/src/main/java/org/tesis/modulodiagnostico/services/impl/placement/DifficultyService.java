package org.tesis.modulodiagnostico.services.impl.placement;

import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.models.placementtests.Difficulties;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;
import org.tesis.modulodiagnostico.repositories.DifficultyRepository;

import java.util.List;

@Service
public class DifficultyService {

    private final DifficultyRepository difficultyRepository;

    public DifficultyService(DifficultyRepository difficultyRepository) {
        this.difficultyRepository = difficultyRepository;
    }

    public Difficulty getDifficultyByName(String name) {
        return this.difficultyRepository.getDifficultiesByName(Difficulties.valueOf(name));
    }

    public List<Difficulty> getAllDifficulties() {
        return this.difficultyRepository.findAll();
    }

}
