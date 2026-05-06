package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.usuarios.EstiloAprendizaje;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEstiloAprendizajeRepository extends JpaRepository<EstiloAprendizaje, Long> {
    Optional<EstiloAprendizaje> findByUsuarioIdAndEstiloVark_Id(Long usuarioId, Long estiloVarkId);
    List<EstiloAprendizaje> findAllByUsuarioId(Long usuarioId);
}