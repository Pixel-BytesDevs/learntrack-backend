package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.cuestionariovark.EstiloVark;

@Repository
public interface IEstiloVarkRepository extends JpaRepository<EstiloVark, Long> {}
