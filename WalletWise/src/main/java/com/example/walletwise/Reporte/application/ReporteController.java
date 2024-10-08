package com.example.walletwise.Reporte.application;

import com.example.walletwise.Reporte.dtos.ReporteDTO;
import com.example.walletwise.Reporte.domain.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @PostMapping
    public ResponseEntity<ReporteDTO> crearReporte(@RequestBody ReporteDTO reporteDTO) {
        return new ResponseEntity<>(reporteService.crearReporte(reporteDTO), HttpStatus.CREATED);
    }
}

