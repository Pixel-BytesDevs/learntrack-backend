package org.tesis.modulodiagnostico.models.cuestionariovark;

import jakarta.persistence.*;
@Entity
@Table(name = "alternativas")
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alternativaNombre;

    @ManyToOne(fetch = FetchType.LAZY)
    private EstiloVark estiloVark;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pregunta pregunta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlternativaNombre() {
        return alternativaNombre;
    }

    public void setAlternativaNombre(String alternativaNombre) {
        this.alternativaNombre = alternativaNombre;
    }

    public EstiloVark getEstiloVark() {
        return estiloVark;
    }

    public void setEstiloVark(EstiloVark estiloVark) {
        this.estiloVark = estiloVark;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}
