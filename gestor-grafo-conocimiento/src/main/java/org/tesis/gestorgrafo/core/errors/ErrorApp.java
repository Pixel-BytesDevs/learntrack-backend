package org.tesis.gestorgrafo.core.errors;

public enum ErrorApp {
    COMPETENCIA_NOT_FOUND("ERROR_001","Esa competencia no existe");



    private final String code;
    private final String message;

    ErrorApp(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
