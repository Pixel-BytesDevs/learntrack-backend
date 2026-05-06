package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.placementtests.Difficulties;
import org.tesis.modulodiagnostico.models.placementtests.Difficulty;

import java.util.Optional;

@Repository
public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Difficulty getDifficultiesByName(Difficulties name);

    Optional<Difficulty> findByName(Difficulties name);
}
