package com.example.walletwise.ObjetivoFinanciero.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
public class ObjetivoFinanciero {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre_Objetivo;

    private double montoObjetivo;

    private ZonedDateTime fecha_Limite;

    private double progreso;

}
