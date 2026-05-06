package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
}
