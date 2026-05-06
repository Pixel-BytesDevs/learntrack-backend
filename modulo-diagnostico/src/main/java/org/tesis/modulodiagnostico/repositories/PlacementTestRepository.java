package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.placementtests.PlacementTest;

import java.util.Optional;

@Repository
public interface PlacementTestRepository extends JpaRepository<PlacementTest, Long> {
    Optional<PlacementTest> findByIdAndUsuarioId(Long id, Long usuarioId);
}

