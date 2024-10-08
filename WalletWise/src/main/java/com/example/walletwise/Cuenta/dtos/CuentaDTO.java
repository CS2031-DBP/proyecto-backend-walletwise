package com.example.walletwise.Cuenta.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaDTO {
    private Long id;
    private String banco;
    private BigDecimal saldo;
    private String tipoCuenta;  // Puede ser "Ahorro", "Corriente", etc.
    private String moneda;      // Ej: "USD", "PEN", etc.
    private Long usuarioId;     // Referencia al usuario propietario de la cuenta
}

