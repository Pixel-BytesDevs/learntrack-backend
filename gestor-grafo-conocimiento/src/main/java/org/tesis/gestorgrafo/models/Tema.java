package org.tesis.gestorgrafo.models;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Data
@Node("Tema")
public class Tema {

    @Id
    private String temaId;
    private String nombre;
    private Integer grado_recomendado;
    private String nivel_dificultad;
    @Relationship(type = "DEPENDE_DE", direction = Relationship.Direction.OUTGOING)
    private List<Tema> temasRequeridos;


    public Tema() {}

    public Tema(String nombre) {
        this.nombre = nombre;
    }

}


