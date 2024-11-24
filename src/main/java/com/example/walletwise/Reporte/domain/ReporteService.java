package com.example.walletwise.Reporte.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Cuenta.domain.Cuenta;
import com.example.walletwise.Cuenta.infrastructure.CuentaRepository;
import com.example.walletwise.Reporte.dtos.CrearReporteDTO;
import com.example.walletwise.Reporte.dtos.ReporteDTO;
import com.example.walletwise.Reporte.infrastructure.ReporteRepository;
import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.infrastructure.TransaccionRepository;
import com.example.walletwise.Usuario.domain.Role;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private TransaccionRepository transaccionRepository;

    public ReporteDTO crearReporte(CrearReporteDTO reporteDTO) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar fechas
        if (reporteDTO.getFechaFin().isBefore(reporteDTO.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        Reporte reporte = new Reporte();
        reporte.setFechaGeneracion(LocalDate.now());
        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setFechaInicio(reporteDTO.getFechaInicio());
        reporte.setFechaFin(reporteDTO.getFechaFin());
        reporte.setFormato(reporteDTO.getFormato());
        reporte.setUsuario(usuario);

        // Obtener las transacciones del usuario para el período especificado
        List<Transaccion> transacciones = transaccionRepository.findByCuentaUsuarioIdAndFechaBetween(
                usuario.getId(), reporte.getFechaInicio(), reporte.getFechaFin());
        reporte.setTransacciones(transacciones);

        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }

    public List<ReporteDTO> obtenerReportesUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Reporte> reportes = reporteRepository.findByUsuarioId(usuario.getId());
        return reportes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ReporteDTO obtenerReportePorId(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Verificar que el reporte pertenece al usuario autenticado
        if (!reporte.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para ver este reporte");
        }

        return mapToDTO(reporte);
    }

    public ReporteDTO actualizarReporte(Long id, CrearReporteDTO reporteDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Verificar que el reporte pertenece al usuario autenticado
        if (!reporte.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para modificar este reporte");
        }

        // Validar fechas si están siendo actualizadas
        if (reporteDTO.getFechaFin().isBefore(reporteDTO.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        reporte.setTipoReporte(reporteDTO.getTipoReporte());
        reporte.setFechaInicio(reporteDTO.getFechaInicio());
        reporte.setFechaFin(reporteDTO.getFechaFin());
        reporte.setFormato(reporteDTO.getFormato());

        // Actualizar las transacciones
        List<Transaccion> transacciones = transaccionRepository.findByCuentaUsuarioIdAndFechaBetween(
                usuario.getId(), reporte.getFechaInicio(), reporte.getFechaFin());
        reporte.setTransacciones(transacciones);

        reporteRepository.save(reporte);
        return mapToDTO(reporte);
    }
    public List<ReporteDTO> obtenerTodosLosReportes() {
        List<Reporte> reportes = reporteRepository.findAll();
        return reportes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ReporteDTO mapToDTO(Reporte reporte) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setId(reporte.getId());
        reporteDTO.setUsuarioId(reporte.getUsuario().getId());
        reporteDTO.setFechaGeneracion(reporte.getFechaGeneracion());
        reporteDTO.setTipoReporte(reporte.getTipoReporte());
        reporteDTO.setFechaInicio(reporte.getFechaInicio());
        reporteDTO.setFechaFin(reporte.getFechaFin());
        reporteDTO.setFormato(reporte.getFormato());

        // Mapear las transacciones
        List<TransaccionDTO> transaccionesDTO = reporte.getTransacciones().stream()
                .map(this::mapTransaccionToDTO)
                .collect(Collectors.toList());
        reporteDTO.setTransacciones(transaccionesDTO);

        return reporteDTO;
    }
    private TransaccionDTO mapTransaccionToDTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setId(transaccion.getId());
        dto.setMonto(transaccion.getMonto());
        dto.setDestinatario(transaccion.getDestinatario());
        dto.setFecha(transaccion.getFecha());
        dto.setTipo(transaccion.getTipo());

        if (transaccion.getCuenta() != null) {
            dto.setCuentaId(transaccion.getCuenta().getId());
        }
        if (transaccion.getCategoria() != null) {
            dto.setCategoriaId(transaccion.getCategoria().getId());
        }

        return dto;
    }

}

