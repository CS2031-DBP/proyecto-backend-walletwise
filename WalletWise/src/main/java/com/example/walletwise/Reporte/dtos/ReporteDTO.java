package com.example.walletwise.Reporte.dtos;

import java.time.LocalDate;

public class ReporteDTO {
    private Long id;
    private Long usuarioId;       // Referencia al usuario que genera el reporte
    private LocalDate fechaGeneracion;
    private String tipoReporte;   // Tipo de reporte, como "Gastos", "Ingresos", "Presupuesto"
    private String contenido;     // Resumen o análisis financiero
    private String rangoFechas;   // Periodo que cubre el reporte, por ejemplo, "01/01/2024 - 31/01/2024"
    private String formato;       // Puede ser "PDF", "CSV", etc.

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getRangoFechas() {
        return rangoFechas;
    }

    public void setRangoFechas(String rangoFechas) {
        this.rangoFechas = rangoFechas;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}

