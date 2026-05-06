package org.tesis.gestorgrafo.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.tesis.gestorgrafo.models.Competencia;

import java.util.Optional;

@Repository
public interface CompetenciaRepository  extends Neo4jRepository<Competencia, Long> {

    Optional<Competencia> findById(Long id);

    Boolean existsCompetenciaByid(Long id);

}
