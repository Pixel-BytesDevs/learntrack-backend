package org.tesis.modulodiagnostico.models.usuarios;


import jakarta.persistence.*;
import org.tesis.modulodiagnostico.models.cuestionariovark.EstiloVark;
import org.tesis.modulodiagnostico.models.sesiones.Alumno;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "estilos_aprendizaje",
        uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id","estilo_vark_id"})
)
public class EstiloAprendizaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estilo_vark_id", nullable = false)
    private EstiloVark estiloVark;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", insertable = false, updatable = false)
    private Alumno alumno;

    @Column(name = "nivel_porcentaje", precision = 7, scale = 2, nullable = false)
    private BigDecimal nivelPorcentaje;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstiloVark getEstiloVark() {
        return estiloVark;
    }

    public void setEstiloVark(EstiloVark estiloVark) {
        this.estiloVark = estiloVark;
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

    public BigDecimal getNivelPorcentaje() {
        return nivelPorcentaje;
    }

    public void setNivelPorcentaje(BigDecimal nivelPorcentaje) {
        this.nivelPorcentaje = nivelPorcentaje;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
