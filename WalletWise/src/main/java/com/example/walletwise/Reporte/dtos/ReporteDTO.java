package com.example.walletwise.Reporte.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReporteDTO {
    private Long id;
    private Long usuarioId;       // Referencia al usuario que genera el reporte
    private LocalDate fechaGeneracion;
    private String tipoReporte;   // Tipo de reporte, como "Gastos", "Ingresos", "Presupuesto"
    private String contenido;     // Resumen o análisis financiero
    private String rangoFechas;   // Periodo que cubre el reporte, por ejemplo, "01/01/2024 - 31/01/2024"
    private String formato;       // Puede ser "PDF", "CSV", etc.

}

