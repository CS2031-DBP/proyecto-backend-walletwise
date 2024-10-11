package com.example.walletwise.Reporte.dtos;

import com.example.walletwise.Reporte.domain.TipoReporte;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReporteDTO {
    private Long id;
    private Long usuarioId;       // Referencia al usuario que genera el reporte
    private LocalDate fechaGeneracion;
    private TipoReporte tipoReporte;   // Tipo de reporte, como "Gastos", "Ingresos", "Presupuesto"
    private String contenido;     // Resumen o análisis financiero
    private String rangoFechas;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String formato;       // Puede ser "PDF", "CSV", etc.

}

