package com.example.walletwise.Transaccion.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransaccionDTO {
    private Long id;
    private BigDecimal monto;
    private String destinatario; // Persona o entidad que recibe el dinero
    private LocalDate fecha;
    private String tipo;         // Puede ser "Ingreso" o "Gasto"
    private Long cuentaId;       // Referencia a la cuenta asociada
    private Long categoriaId;    // Referencia a la categoría de la transacción

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}

