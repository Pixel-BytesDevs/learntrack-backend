package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;

@Entity
@Table(name = "placement_types")
public class PlacementType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PlacementTypes type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlacementTypes getType() {
        return type;
    }

    public void setType(PlacementTypes type) {
        this.type = type;
    }
}
