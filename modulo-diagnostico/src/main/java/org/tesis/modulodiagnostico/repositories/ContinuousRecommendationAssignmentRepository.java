package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tesis.modulodiagnostico.models.recommendations.ContinuousRecommendationAssignment;

import java.util.Optional;

public interface ContinuousRecommendationAssignmentRepository extends JpaRepository<ContinuousRecommendationAssignment, Long> {

    Optional<ContinuousRecommendationAssignment> findTopByPlacementTestIdAndUserIdOrderByCreatedAtDesc(Long placementTestId, Long userId);
}
