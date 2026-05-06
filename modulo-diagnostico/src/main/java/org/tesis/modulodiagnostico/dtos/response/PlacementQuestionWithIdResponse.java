package org.tesis.modulodiagnostico.dtos.response;

public class PlacementQuestionWithIdResponse extends PlacementQuestionResponse{
    private Long id;

    public PlacementQuestionWithIdResponse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}