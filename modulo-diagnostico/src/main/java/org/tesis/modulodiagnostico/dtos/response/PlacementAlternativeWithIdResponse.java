package org.tesis.modulodiagnostico.dtos.response;

public class PlacementAlternativeWithIdResponse extends PlacementAlternativeResponse{
    private Long id;

    public PlacementAlternativeWithIdResponse() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}