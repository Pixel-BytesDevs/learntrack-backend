package org.tesis.modulodiagnostico.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tesis.modulodiagnostico.dtos.request.AlumnoDataRequest;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;
import org.tesis.modulodiagnostico.models.sesiones.CursoAula;
import org.tesis.modulodiagnostico.models.sesiones.CursoAulaAlumno;
import org.tesis.modulodiagnostico.repositories.AlumnoRepository;
import org.tesis.modulodiagnostico.repositories.CursoAulaAlumnoRepository;
import org.tesis.modulodiagnostico.repositories.CursoAulaRepository;

@Service
public class AlumnoService {

    private AlumnoRepository alumnoRepository;

    private CursoAulaRepository cursoAulaRepository;

    private CursoAulaAlumnoRepository cursoAulaAlumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository, CursoAulaRepository cursoAulaRepository, CursoAulaAlumnoRepository cursoAulaAlumnoRepository) {
        this.alumnoRepository = alumnoRepository;
        this.cursoAulaRepository = cursoAulaRepository;
        this.cursoAulaAlumnoRepository = cursoAulaAlumnoRepository;
    }

    public String createAlumno(AlumnoDataRequest request) {

        // 1. Crear alumno
        Alumno alumno = new Alumno();
        alumno.setUsuarioId(request.getUsuarioId());
        alumno.setPrimerNombre(request.getPrimerNombre());
        alumno.setApellidoPaterno(request.getApellidoPaterno());
        alumno.setApellidoMaterno(request.getApellidoMaterno());
        alumno.setEdad(request.getEdad());
        alumno.setGenero(request.getGenero());

        Alumno alumnoGuardado = alumnoRepository.save(alumno);

        // 2. Buscar cursoAula
        CursoAula cursoAula = cursoAulaRepository.findById(request.getIdSalon())
                .orElseThrow(() -> new RuntimeException("CursoAula no encontrado"));

        // 3. Crear relación curso_aula_alumno
        CursoAulaAlumno relacion = new CursoAulaAlumno();
        relacion.setAlumno(alumnoGuardado);
        relacion.setCursoAula(cursoAula);

        cursoAulaAlumnoRepository.save(relacion);

        return "Alumno creado correctamente";
    }
}
