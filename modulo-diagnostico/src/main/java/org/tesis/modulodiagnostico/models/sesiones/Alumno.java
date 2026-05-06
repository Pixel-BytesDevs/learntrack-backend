package org.tesis.modulodiagnostico.models.sesiones;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "genero")
    private String genero;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    private List<CursoAulaAlumno> cursos;

    public Alumno() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<CursoAulaAlumno> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoAulaAlumno> cursos) {
        this.cursos = cursos;
    }
}