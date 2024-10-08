package com.example.walletwise.Presupuesto.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PresupuestoDTO {
    private Long id;
    private BigDecimal montoTotal;
    private BigDecimal gastoActual;
    private String categoria;
    private String alerta;       // Condición para emitir alertas
    private String periodo;      // Puede ser "Mensual", "Anual", etc.
    private Long usuarioId;      // Referencia al usuario propietario del presupuesto
}

