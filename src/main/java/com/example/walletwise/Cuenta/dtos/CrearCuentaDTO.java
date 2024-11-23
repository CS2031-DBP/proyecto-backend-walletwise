package com.example.walletwise.Cuenta.dtos;

import com.example.walletwise.Cuenta.domain.Moneda;
import com.example.walletwise.Cuenta.domain.TipoCuenta;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CrearCuentaDTO {
    private String nombre;
    private BigDecimal saldo;
    private TipoCuenta tipoCuenta;
    private Moneda moneda;
}