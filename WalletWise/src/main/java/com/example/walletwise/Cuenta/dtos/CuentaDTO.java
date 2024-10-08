package com.example.walletwise.Cuenta.dtos;

import java.math.BigDecimal;

public class CuentaDTO {
    private Long id;
    private String banco;
    private BigDecimal saldo;
    private String tipoCuenta;  // Puede ser "Ahorro", "Corriente", etc.
    private String moneda;      // Ej: "USD", "PEN", etc.
    private Long usuarioId;     // Referencia al usuario propietario de la cuenta

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}

