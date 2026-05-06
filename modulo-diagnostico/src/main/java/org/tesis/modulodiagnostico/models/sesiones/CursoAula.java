package org.tesis.modulodiagnostico.models.sesiones;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_aula")
public class CursoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "profesor_id")
    private Long profesorId;

    @ManyToOne
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "total_inscritos")
    private Integer totalInscritos;

    @Column(name = "promedio_general")
    private Double promedioGeneral;

    public CursoAula() {
    }

    public Long getId() {
        return id;
    }

    public Long getProfesorId() {
        return profesorId;
    }

    public Aula getAula() {
        return aula;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getTotalInscritos() {
        return totalInscritos;
    }

    public Double getPromedioGeneral() {
        return promedioGeneral;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTotalInscritos(Integer totalInscritos) {
        this.totalInscritos = totalInscritos;
    }

    public void setPromedioGeneral(Double promedioGeneral) {
        this.promedioGeneral = promedioGeneral;
    }
}