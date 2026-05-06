package org.tesis.modulodiagnostico.models.placementtests;

public enum Difficulties {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final Integer weigth;

    Difficulties(Integer weigth) {
        this.weigth = weigth;
    }

    public Integer getWeigth() {
        return weigth;
    }

}
