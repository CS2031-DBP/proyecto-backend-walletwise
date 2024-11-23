// src/main/java/com/example/walletwise/Presupuesto/dtos/CrearPresupuestoDTO.java
package com.example.walletwise.Presupuesto.dtos;

import com.example.walletwise.Presupuesto.domain.PeriodoPresupuesto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CrearPresupuestoDTO {
    private BigDecimal montoTotal;
    private BigDecimal gastoActual;
    private Long categoriaId;
    private String alerta;
    private PeriodoPresupuesto periodo;
}