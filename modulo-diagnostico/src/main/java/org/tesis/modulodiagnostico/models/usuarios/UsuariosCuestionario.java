package org.tesis.modulodiagnostico.models.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.tesis.modulodiagnostico.models.cuestionariovark.Alternativa;
import org.tesis.modulodiagnostico.models.cuestionariovark.Pregunta;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;

@Entity
@Table(name = "usuarios_cuestionario",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"usuario_id","pregunta_id","alternativa_id"})
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuariosCuestionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alternativa_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Alternativa alternativa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }
}
