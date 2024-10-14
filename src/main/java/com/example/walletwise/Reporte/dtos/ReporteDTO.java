package com.example.walletwise.Reporte.dtos;

import com.example.walletwise.Reporte.domain.TipoReporte;
import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReporteDTO {
    private Long id;
    private Long usuarioId;
    private LocalDate fechaGeneracion;
    private TipoReporte tipoReporte;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String formato;
    private List<TransaccionDTO> transacciones;
}

