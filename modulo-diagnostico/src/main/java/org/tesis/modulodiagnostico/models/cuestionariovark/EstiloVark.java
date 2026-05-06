package org.tesis.modulodiagnostico.models.cuestionariovark;

import jakarta.persistence.*;

@Entity
@Table(name = "estilos_vark")
public class EstiloVark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoEstiloVark tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEstiloVark getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstiloVark tipo) {
        this.tipo = tipo;
    }
}
