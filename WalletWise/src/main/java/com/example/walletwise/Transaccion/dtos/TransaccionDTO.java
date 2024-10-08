package com.example.walletwise.Transaccion.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransaccionDTO {
    private Long id;
    private BigDecimal monto;
    private String destinatario; // Persona o entidad que recibe el dinero
    private LocalDate fecha;
    private String tipo;         // Puede ser "Ingreso" o "Gasto"
    private Long cuentaId;       // Referencia a la cuenta asociada
    private Long categoriaId;    // Referencia a la categoría de la transacción

}

