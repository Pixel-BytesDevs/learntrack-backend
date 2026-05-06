package org.tesis.modulodiagnostico.models;

import jakarta.persistence.*;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    private String id;
    private String name;

    @Column(name = "is_principal", nullable = false)
    private Boolean isPrincipal = false;

    @Column(name = "is_basic", nullable = false)
    private Boolean isBasic = false;

    @Column(name = "is_advanced", nullable = false)
    private Boolean isAdvanced = false;

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

    public Boolean getIsPrincipal() {
        return isPrincipal;
    }

    public void setIsPrincipal(Boolean principal) {
        isPrincipal = principal;
    }

    public Boolean getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(Boolean basic) {
        isBasic = basic;
    }

    public Boolean getIsAdvanced() {
        return isAdvanced;
    }

    public void setIsAdvanced(Boolean advanced) {
        isAdvanced = advanced;
    }
}
