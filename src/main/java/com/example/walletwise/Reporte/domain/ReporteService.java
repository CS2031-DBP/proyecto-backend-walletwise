package com.example.walletwise.Reporte.domain;

import com.example.walletwise.Reporte.dtos.ReporteDTO;
import com.example.walletwise.Reporte.infrastructure.ReporteRepository;
import com.example.walletwise.Usuario.domain.Role;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;


    @Autowired
    private UsuarioRepository usuarioRepository;

    public ReporteDTO crearReporte(ReporteDTO reporteDTO) {
        // Validar fechas
        if (reporteDTO.getFechaFin().isBefore(reporteDTO.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        Reporte reporte = new Reporte();
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setFechaInicio(reporteDTO.getFechaInicio());
        reporte.setFechaFin(reporteDTO.getFechaFin());
        reporte.setFormato(reporteDTO.getFormato());

        // Asignar Usuario al Reporte
        Usuario usuario = usuarioRepository.findById(reporteDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + reporteDTO.getUsuarioId()));
        reporte.setUsuario(usuario);

        // Generar el archivo del reporte (opcional)
        // String urlArchivo = generarArchivoReporte(reporte);
        // reporte.setUrlArchivo(urlArchivo);

        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }

    public List<ReporteDTO> obtenerTodosLosReportes() {
        List<Reporte> reportes = reporteRepository.findAll();
        return reportes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ReporteDTO obtenerReportePorId(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));
        return mapToDTO(reporte);
    }


    public ReporteDTO actualizarReporte(Long id, ReporteDTO reporteDTO) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Validar fechas si están siendo actualizadas
        if (reporteDTO.getFechaInicio() != null && reporteDTO.getFechaFin() != null) {
            if (reporteDTO.getFechaFin().isBefore(reporteDTO.getFechaInicio())) {
                throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
            }
            reporte.setFechaInicio(reporteDTO.getFechaInicio());
            reporte.setFechaFin(reporteDTO.getFechaFin());
        }

        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setContenido(reporteDTO.getContenido());
        reporte.setFormato(reporteDTO.getFormato());

        // Actualizar Usuario si es necesario
        if (reporteDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(reporteDTO.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + reporteDTO.getUsuarioId()));
            reporte.setUsuario(usuario);
        }

        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }

    public void eliminarReporte(Long id) {
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));
        reporteRepository.delete(reporte);
    }

    private ReporteDTO mapToDTO(Reporte reporte) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setId(reporte.getId());
        reporteDTO.setTipoReporte(reporte.getTipoReporte());
        reporteDTO.setContenido(reporte.getContenido());
        reporteDTO.setFechaInicio(reporte.getFechaInicio());
        reporteDTO.setFechaFin(reporte.getFechaFin());
        reporteDTO.setFormato(reporte.getFormato());
        return reporteDTO;
    }
}

