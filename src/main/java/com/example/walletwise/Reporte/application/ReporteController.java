package com.example.walletwise.Reporte.application;

import com.example.walletwise.Reporte.dtos.ReporteDTO;
import com.example.walletwise.Reporte.domain.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Crear una nueva Reporte
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReporteDTO> crearReporte(@Valid @RequestBody ReporteDTO reporteDTO) {
        ReporteDTO creado = reporteService.crearReporte(reporteDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // Obtener todos los reportes (Solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReporteDTO>> obtenerTodosLosReportes() {
        List<ReporteDTO> reportes = reporteService.obtenerTodosLosReportes();
        return ResponseEntity.ok(reportes);
    }

    // Obtener un reporte por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReporteDTO> obtenerReportePorId(@PathVariable Long id) {
        ReporteDTO reporteDTO = reporteService.obtenerReportePorId(id);
        return ResponseEntity.ok(reporteDTO);
    }

    // Actualizar un reporte existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ReporteDTO> actualizarReporte(@PathVariable Long id, @Valid @RequestBody ReporteDTO reporteDTO) {
        ReporteDTO actualizado = reporteService.actualizarReporte(id, reporteDTO);
        return ResponseEntity.ok(actualizado);
    }

}

