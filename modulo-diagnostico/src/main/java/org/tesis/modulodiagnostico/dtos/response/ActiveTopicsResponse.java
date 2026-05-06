package org.tesis.modulodiagnostico.dtos.response;

import java.math.BigDecimal;

public class ActiveTopicsResponse {
    private String id;
    private String name;
    private BigDecimal domain;

    public ActiveTopicsResponse(String id, String name, BigDecimal domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDomain() {
        return domain;
    }

    public void setDomain(BigDecimal domain) {
        this.domain = domain;
    }
}
