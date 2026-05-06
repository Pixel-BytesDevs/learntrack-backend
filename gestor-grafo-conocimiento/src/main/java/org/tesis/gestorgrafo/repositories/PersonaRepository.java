package org.tesis.gestorgrafo.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.tesis.gestorgrafo.models.Persona;

@Repository
public interface PersonaRepository extends Neo4jRepository<Persona, Long> {
}
