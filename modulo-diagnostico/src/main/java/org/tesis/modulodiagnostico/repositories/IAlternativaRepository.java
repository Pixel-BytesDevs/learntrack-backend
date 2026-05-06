package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.cuestionariovark.Alternativa;

@Repository
public interface IAlternativaRepository extends JpaRepository<Alternativa, Long> {
}
