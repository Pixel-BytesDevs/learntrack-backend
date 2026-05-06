package org.tesis.gestorgrafo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class CompetenciaDTO {

    @JsonProperty("document_url")
    private String documentUrl;

    private String curso;

    @JsonProperty("competencia_base")
    private String competenciaBase;


    private String nivel;

    private List<NodoDTO> nodos;


}
