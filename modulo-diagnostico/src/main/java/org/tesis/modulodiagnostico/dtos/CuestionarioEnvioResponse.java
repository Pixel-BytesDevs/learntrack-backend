package org.tesis.modulodiagnostico.dtos;

import java.util.List;

public class CuestionarioEnvioResponse {

    private Long usuarioId;
    private int preguntasProcesadas;
    private int registrosInsertados;
    private String message;
    private List<PreguntaResultado> detalles;

    // ⬇️ ESTE ES EL CAMPO QUE PREGUNTAS
    private List<EstiloDominioResponse> estilos;

    // ---- nested DTO para detallar cada pregunta ----
    public static class PreguntaResultado {
        private Long preguntaId;
        private List<Long> alternativaIds;

        public PreguntaResultado() { }
        public PreguntaResultado(Long preguntaId, List<Long> alternativaIds) {
            this.preguntaId = preguntaId;
            this.alternativaIds = alternativaIds;
        }

        public Long getPreguntaId() { return preguntaId; }
        public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }

        public List<Long> getAlternativaIds() { return alternativaIds; }
        public void setAlternativaIds(List<Long> alternativaIds) { this.alternativaIds = alternativaIds; }
    }

    // ---- getters/setters ----
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public int getPreguntasProcesadas() { return preguntasProcesadas; }
    public void setPreguntasProcesadas(int preguntasProcesadas) { this.preguntasProcesadas = preguntasProcesadas; }

    public int getRegistrosInsertados() { return registrosInsertados; }
    public void setRegistrosInsertados(int registrosInsertados) { this.registrosInsertados = registrosInsertados; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<PreguntaResultado> getDetalles() { return detalles; }
    public void setDetalles(List<PreguntaResultado> detalles) { this.detalles = detalles; }

    // ⬇️ getters/setters de "estilos"
    public List<EstiloDominioResponse> getEstilos() { return estilos; }
    public void setEstilos(List<EstiloDominioResponse> estilos) { this.estilos = estilos; }

}
