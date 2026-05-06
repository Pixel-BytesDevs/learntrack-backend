package org.tesis.modulodiagnostico.models;

import jakarta.persistence.*;

@Entity
@Table(name = "learning_objects")
public class LearningObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Enumerated(EnumType.STRING)
    private OATypes type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OATypes getType() {
        return type;
    }

    public void setType(OATypes type) {
        this.type = type;
    }
}
