package com.example.walletwise.Reporte.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Cuenta.domain.Cuenta;
import com.example.walletwise.Cuenta.infrastructure.CuentaRepository;
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

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public ReporteDTO crearReporte(ReporteDTO reporteDTO) {
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

        // Asignar Usuario al Reporte
        Usuario usuario = usuarioRepository.findById(reporteDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + reporteDTO.getUsuarioId()));
        reporte.setUsuario(usuario);

        // Obtener las transacciones del usuario para el período especificado
        List<Transaccion> transacciones = transaccionRepository.findByCuentaIdAndFechaBetween(
                usuario.getId(), reporte.getFechaInicio(), reporte.getFechaFin());
        reporte.setTransacciones(transacciones);

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
        reporte.setFormato(reporteDTO.getFormato());

        // Actualizar Usuario si es necesario
        if (reporteDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(reporteDTO.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + reporteDTO.getUsuarioId()));
            reporte.setUsuario(usuario);
        }

        // Actualizar las transacciones si se proporcionan
        if (reporteDTO.getTransacciones() != null) {
            List<Transaccion> transacciones = reporteDTO.getTransacciones().stream()
                    .map(this::mapDTOToTransaccion)
                    .collect(Collectors.toList());
            reporte.setTransacciones(transacciones);
        } else {
            // Si no se proporcionan transacciones, obtener las transacciones actualizadas del usuario
            List<Transaccion> transacciones = transaccionRepository.findByCuentaIdAndFechaBetween(
                    reporte.getUsuario().getId(), reporte.getFechaInicio(), reporte.getFechaFin());
            reporte.setTransacciones(transacciones);
        }

        reporteRepository.save(reporte);
        return mapToDTO(reporte);
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

        // Mapear IDs de Cuenta y Categoría
        if (transaccion.getCuenta() != null) {
            dto.setCuentaId(transaccion.getCuenta().getId());
        }
        if (transaccion.getCategoria() != null) {
            dto.setCategoriaId(transaccion.getCategoria().getId());
        }

        return dto;
    }

    private Transaccion mapDTOToTransaccion(TransaccionDTO dto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(dto.getId());
        transaccion.setMonto(dto.getMonto());
        transaccion.setDestinatario(dto.getDestinatario());
        transaccion.setFecha(dto.getFecha());
        transaccion.setTipo(dto.getTipo());

        // Obtener la cuenta y la categoría basándose en los IDs proporcionados
        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + dto.getCuentaId()));
        transaccion.setCuenta(cuenta);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + dto.getCategoriaId()));
        transaccion.setCategoria(categoria);

        return transaccion;
    }
}

