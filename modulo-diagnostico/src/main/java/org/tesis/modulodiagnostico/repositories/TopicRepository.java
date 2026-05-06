package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.Topic;

import java.util.Collection;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

    List<Topic> findByNameIn(Collection<String> names);

    List<Topic> findByIsPrincipalTrue();

    List<Topic> findByIsBasicTrue();

    List<Topic> findByIsAdvancedTrue();
}
