package org.tesis.modulodiagnostico.models.sesiones;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_aula_alumno")
public class CursoAulaAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "curso_aula_id")
    private CursoAula cursoAula;

    @Column(name = "nota_pre_test")
    private Double notaPreTest;

    @Column(name = "nota_post_test")
    private Double notaPostTest;

    @Column(name = "promedio")
    private Double promedio;

    public CursoAulaAlumno() {
    }

    public Long getId() {
        return id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public CursoAula getCursoAula() {
        return cursoAula;
    }

    public Double getNotaPreTest() {
        return notaPreTest;
    }

    public Double getNotaPostTest() {
        return notaPostTest;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void setCursoAula(CursoAula cursoAula) {
        this.cursoAula = cursoAula;
    }

    public void setNotaPreTest(Double notaPreTest) {
        this.notaPreTest = notaPreTest;
    }

    public void setNotaPostTest(Double notaPostTest) {
        this.notaPostTest = notaPostTest;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
}
