package org.tesis.gestorgrafo.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.util.List;

@Data
@Node("Competencia")
public class Competencia {

    @Id @GeneratedValue
    private Long id;

    private String descripcion;

    // es un enum
    private NivelCompetencia nivel;

    private String area;

    private String curriculoRef;

    @Relationship(type = "ABARCA", direction = Relationship.Direction.OUTGOING)
    private List<Tema> temas;

    public Competencia() {}

    public Competencia(String descripcion, NivelCompetencia nivel, String area, String curriculoRef) {
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.area = area;
        this.curriculoRef = curriculoRef;
    }

}
