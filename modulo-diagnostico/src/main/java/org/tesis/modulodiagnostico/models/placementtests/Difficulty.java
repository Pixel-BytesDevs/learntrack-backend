package org.tesis.modulodiagnostico.models.placementtests;

import jakarta.persistence.*;

@Entity
@Table(name = "difficulties")
public class Difficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Difficulties name;

    private Integer weigth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Difficulties getName() {
        return name;
    }

    public void setName(Difficulties name) {
        this.name = name;
    }

    public Integer getWeigth() {
        return weigth;
    }

    public void setWeigth(Integer weigth) {
        this.weigth = weigth;
    }
}
