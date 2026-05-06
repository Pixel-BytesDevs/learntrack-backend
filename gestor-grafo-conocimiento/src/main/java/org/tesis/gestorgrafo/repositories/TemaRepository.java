package org.tesis.gestorgrafo.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.tesis.gestorgrafo.models.Tema;

@Repository
public interface TemaRepository extends Neo4jRepository<Tema, String> {
    Tema findByTemaId(String temaId);
}
