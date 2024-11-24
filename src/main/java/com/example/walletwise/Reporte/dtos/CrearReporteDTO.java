package com.example.walletwise.Reporte.dtos;

import com.example.walletwise.Reporte.domain.TipoReporte;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CrearReporteDTO {
    @NotNull(message = "El tipo de reporte es obligatorio")
    private TipoReporte tipoReporte;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotNull(message = "El formato es obligatorio")
    private String formato; // "PDF", "CSV", etc.
}