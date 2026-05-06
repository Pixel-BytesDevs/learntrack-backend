package org.tesis.modulodiagnostico.dtos;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

public class CuestionarioEnvioRequest {

    @NotNull
    private Long usuarioId;

    @NotNull
    private List<RespuestaPreguntaRequest> respuestas;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<RespuestaPreguntaRequest> getRespuestas() { return respuestas; }
    public void setRespuestas(List<RespuestaPreguntaRequest> respuestas) { this.respuestas = respuestas; }

    public static class RespuestaPreguntaRequest {
        @NotNull
        private Long preguntaId;

        @NotNull
        private List<Long> alternativaIds;

        public Long getPreguntaId() { return preguntaId; }
        public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }

        public List<Long> getAlternativaIds() { return alternativaIds; }
        public void setAlternativaIds(List<Long> alternativaIds) { this.alternativaIds = alternativaIds; }
    }
}
