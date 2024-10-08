package com.example.walletwise.service;

import com.example.walletwise.domain.Reporte;
import com.example.walletwise.dto.ReporteDTO;
import com.example.walletwise.infrastructure.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public ReporteDTO crearReporte(ReporteDTO reporteDTO) {
        Reporte reporte = new Reporte();
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setRangoFechas(reporteDTO.getRangoFechas());
        reporte.setFormato(reporteDTO.getFormato());
        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }

    private ReporteDTO mapToDTO(Reporte reporte) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setId(reporte.getId());
        reporteDTO.setTipoReporte(reporte.getTipoReporte());
        reporteDTO.setContenido(reporte.getContenido());
        reporteDTO.setRangoFechas(reporte.getRangoFechas());
        reporteDTO.setFormato(reporte.getFormato());
        return reporteDTO;
    }
}

