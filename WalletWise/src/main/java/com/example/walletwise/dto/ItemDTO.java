package com.example.walletwise.dto;

import java.math.BigDecimal;

public class ItemDTO {
    private Long id;
    private String nombre;       // Nombre del ítem, por ejemplo, "Supermercado"
    private BigDecimal precio;   // Precio del ítem
    private Long transaccionId;  // Referencia a la transacción donde está incluido el ítem

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }
}

