package com.example.walletwise.Cuenta.dtos;

import com.example.walletwise.Cuenta.domain.Moneda;
import com.example.walletwise.Cuenta.domain.TipoCuenta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CuentaDTO {
    private Long id;
    private String nombre;
    private BigDecimal saldo;
    private TipoCuenta tipoCuenta; // Enum aplicado  // Puede ser "Ahorro", "Corriente", etc.
    private Moneda moneda;      // Ej: "USD", "PEN", etc.
    private Long usuarioId;     // Referencia al usuario propietario de la cuenta
}

