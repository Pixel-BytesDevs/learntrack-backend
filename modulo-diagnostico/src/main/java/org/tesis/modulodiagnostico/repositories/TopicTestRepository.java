package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.Topic;
import org.tesis.modulodiagnostico.models.placementtests.PlacementTest;
import org.tesis.modulodiagnostico.models.placementtests.TopicTest;

import java.util.List;

@Repository
public interface TopicTestRepository extends JpaRepository<TopicTest, Long> {

    List<TopicTest> findAllByPlacementTest(PlacementTest placementTest);

    TopicTest findByTopic(Topic topic);
}
