package com.example.walletwise.CuentaFinanciera.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CuentaFinanciera {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String tipo_cuenta;

    private String nombre_cuenta;

    private double saldo;

    private String moneda;

}
