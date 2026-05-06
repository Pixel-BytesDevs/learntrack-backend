package org.tesis.modulodiagnostico.dtos.response;

public class OARecomendationResponse {
    private Long id;
    private String name;
    private String oaType;
    private String url;
    private String description;
    private String difficulty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOaType() {
        return oaType;
    }

    public void setOaType(String oaType) {
        this.oaType = oaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
