package org.tesis.modulodiagnostico.models.sesiones;

import jakarta.persistence.*;

@Entity
@Table(name = "aula")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grado")
    private String grado;

    @Column(name = "seccion")
    private String seccion;

    @Column(name = "cantidad_alumnos")
    private Integer cantidadAlumnos;

    public Aula() {
    }

    public Long getId() {
        return id;
    }

    public String getGrado() {
        return grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public Integer getCantidadAlumnos() {
        return cantidadAlumnos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setCantidadAlumnos(Integer cantidadAlumnos) {
        this.cantidadAlumnos = cantidadAlumnos;
    }
}
