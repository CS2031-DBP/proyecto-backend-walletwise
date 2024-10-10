package com.example.walletwise.Presupuesto.dtos;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.domain.TipoCategoria;
import com.example.walletwise.Presupuesto.domain.PeriodoPresupuesto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PresupuestoDTO {
    private Long id;
    private BigDecimal montoTotal;
    private BigDecimal gastoActual;
    private Long categoriaId;       // Referencia a la categoría del presupuesto
    private String alerta;          // Condición para emitir alertas
    private PeriodoPresupuesto periodo; // Puede ser "MENSUAL", "ANUAL", etc.
    private Long usuarioId;         // Referencia al usuario propietario del presupuesto
}


