package com.example.walletwise.Transaccion.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Cuenta.domain.Cuenta;
import com.example.walletwise.Cuenta.infrastructure.CuentaRepository;
import com.example.walletwise.Presupuesto.domain.Presupuesto;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.infrastructure.TransaccionRepository;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import com.example.walletwise.events.TransactionEvent;  // Importar el evento
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;  // Importar el publisher de eventos
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;  // Inyectar el publisher de eventos

    @Transactional
    public TransaccionDTO crearTransaccion(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setDestinatario(transaccionDTO.getDestinatario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setTipo(transaccionDTO.getTipo());

        Cuenta cuenta = cuentaRepository.findById(transaccionDTO.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + transaccionDTO.getCuentaId()));
        transaccion.setCuenta(cuenta);

        Categoria categoria = categoriaRepository.findById(transaccionDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + transaccionDTO.getCategoriaId()));
        transaccion.setCategoria(categoria);

        transaccionRepository.save(transaccion);

        // Actualizar el presupuesto correspondiente
        actualizarPresupuesto(cuenta.getUsuario().getId(), categoria.getId(), transaccion.getMonto(), transaccion.getTipo());

        String email = cuenta.getUsuario().getEmail();
        eventPublisher.publishEvent(new TransactionEvent(this, email, transaccion));

        return mapToDTO(transaccion);
    }

    private void actualizarPresupuesto(Long usuarioId, Long categoriaId, BigDecimal monto, TipoTransaccion tipo) {
        Presupuesto presupuesto = presupuestoRepository.findByUsuarioIdAndCategoriaId(usuarioId, categoriaId)
                .stream().findFirst().orElse(null);

        if (presupuesto != null) {
            if (tipo == TipoTransaccion.GASTO) {
                presupuesto.setGastoActual(presupuesto.getGastoActual().add(monto));
            } else if (tipo == TipoTransaccion.INGRESO) {
                // Si es un ingreso, podrías restar del gasto actual o manejarlo de otra manera según tu lógica de negocio
                presupuesto.setGastoActual(presupuesto.getGastoActual().subtract(monto));
            }
            presupuestoRepository.save(presupuesto);
        }
    }

    public List<TransaccionDTO> obtenerTodasLasTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        return transacciones.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public TransaccionDTO obtenerTransaccionPorId(Long id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + id));
        return mapToDTO(transaccion);
    }

    public List<TransaccionDTO> obtenerTransaccionesPorUsuarioId(Long usuarioId) {
        List<Transaccion> transacciones = transaccionRepository.findByCuentaUsuarioId(usuarioId);
        return transacciones.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public TransaccionDTO actualizarTransaccion(Long id, TransaccionDTO transaccionDTO) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + id));

        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setDestinatario(transaccionDTO.getDestinatario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setTipo(transaccionDTO.getTipo());

        // Actualizar Cuenta si es necesario
        if (transaccionDTO.getCuentaId() != null) {
            Cuenta cuenta = cuentaRepository.findById(transaccionDTO.getCuentaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + transaccionDTO.getCuentaId()));
            transaccion.setCuenta(cuenta);
        }

        // Actualizar Categoria si es necesario
        if (transaccionDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(transaccionDTO.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + transaccionDTO.getCategoriaId()));
            transaccion.setCategoria(categoria);
        }

        transaccionRepository.save(transaccion);
        return mapToDTO(transaccion);
    }

    public void eliminarTransaccion(Long id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacción no encontrada con id: " + id));
        transaccionRepository.delete(transaccion);
    }

    private TransaccionDTO mapToDTO(Transaccion transaccion) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setId(transaccion.getId());
        transaccionDTO.setMonto(transaccion.getMonto());
        transaccionDTO.setDestinatario(transaccion.getDestinatario());
        transaccionDTO.setFecha(transaccion.getFecha());
        transaccionDTO.setTipo(transaccion.getTipo());
        // Asignar los IDs de cuenta y categoría
        if (transaccion.getCuenta() != null) {
            transaccionDTO.setCuentaId(transaccion.getCuenta().getId());
        }
        if (transaccion.getCategoria() != null) {
            transaccionDTO.setCategoriaId(transaccion.getCategoria().getId());
        }

        return transaccionDTO;
    }
}
