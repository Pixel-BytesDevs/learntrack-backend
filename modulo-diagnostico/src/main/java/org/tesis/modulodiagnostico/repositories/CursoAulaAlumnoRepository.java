package org.tesis.modulodiagnostico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;
import org.tesis.modulodiagnostico.models.sesiones.CursoAulaAlumno;

@Repository
public interface CursoAulaAlumnoRepository extends JpaRepository<CursoAulaAlumno, Long> {
}