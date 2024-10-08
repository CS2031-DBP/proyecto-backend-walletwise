package com.example.walletwise.Presupuesto.dtos;

import java.math.BigDecimal;

public class PresupuestoDTO {
    private Long id;
    private BigDecimal montoTotal;
    private BigDecimal gastoActual;
    private String categoria;
    private String alerta;       // Condición para emitir alertas
    private String periodo;      // Puede ser "Mensual", "Anual", etc.
    private Long usuarioId;      // Referencia al usuario propietario del presupuesto

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getGastoActual() {
        return gastoActual;
    }

    public void setGastoActual(BigDecimal gastoActual) {
        this.gastoActual = gastoActual;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

