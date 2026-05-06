package org.tesis.modulodiagnostico.dtos;

import java.math.BigDecimal;

public class EstiloDominioResponse {
    private Long estiloVarkId;
    private String tipo;           // VISUAL/AUDITIVO/KINESTESICO/LECTURA_ESCRITURA
    private BigDecimal porcentaje; // con 2 decimales

    public EstiloDominioResponse() {}
    public EstiloDominioResponse(Long estiloVarkId, String tipo, BigDecimal porcentaje) {
        this.estiloVarkId = estiloVarkId;
        this.tipo = tipo;
        this.porcentaje = porcentaje;
    }
    public Long getEstiloVarkId() { return estiloVarkId; }
    public String getTipo() { return tipo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setEstiloVarkId(Long estiloVarkId) { this.estiloVarkId = estiloVarkId; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

}
