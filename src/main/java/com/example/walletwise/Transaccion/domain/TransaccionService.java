package com.example.walletwise.Transaccion.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Cuenta.domain.Cuenta;
import com.example.walletwise.Cuenta.infrastructure.CuentaRepository;
import com.example.walletwise.Presupuesto.domain.Presupuesto;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.infrastructure.TransaccionRepository;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import com.example.walletwise.events.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public TransaccionDTO crearTransaccion(TransaccionDTO transaccionDTO) {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setDestinatario(transaccionDTO.getDestinatario());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setTipo(transaccionDTO.getTipo());

        // Obtener la cuenta
        Cuenta cuenta = cuentaRepository.findById(transaccionDTO.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + transaccionDTO.getCuentaId()));
        transaccion.setCuenta(cuenta);

        // Actualizar el saldo de la cuenta
        BigDecimal nuevoSaldo;
        if (transaccionDTO.getTipo() == TipoTransaccion.GASTO) {
            nuevoSaldo = cuenta.getSaldo().subtract(transaccionDTO.getMonto());
        } else if (transaccionDTO.getTipo() == TipoTransaccion.INGRESO) {
            nuevoSaldo = cuenta.getSaldo().add(transaccionDTO.getMonto());
        } else {
            throw new IllegalArgumentException("Tipo de transacción inválido");
        }
        cuenta.setSaldo(nuevoSaldo);  // Actualizamos el saldo de la cuenta
        cuentaRepository.save(cuenta);  // Guardamos los cambios en la cuenta

        // Obtener la categoría
        Categoria categoria = categoriaRepository.findById(transaccionDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + transaccionDTO.getCategoriaId()));
        transaccion.setCategoria(categoria);

        // Guardar la transacción
        transaccionRepository.save(transaccion);

        // Actualizar el presupuesto correspondiente
        actualizarPresupuesto(cuenta.getUsuario().getId(), categoria.getId(), transaccion.getMonto(), transaccion.getTipo());

        // Enviar el evento de la transacción
        String email = cuenta.getUsuario().getEmail();
        eventPublisher.publishEvent(new TransactionEvent(this, email, transaccion));

        return mapToDTO(transaccion);
    }
    public Page<TransaccionDTO> obtenerTransaccionesUsuarioAutenticado(Pageable pageable) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Page<Transaccion> transacciones = transaccionRepository.findByCuentaUsuarioId(usuario.getId(), pageable);
        return transacciones.map(this::mapToDTO);
    }

    private void actualizarPresupuesto(Long usuarioId, Long categoriaId, BigDecimal monto, TipoTransaccion tipo) {
        Presupuesto presupuesto = presupuestoRepository.findByUsuarioIdAndCategoriaId(usuarioId, categoriaId)
                .stream().findFirst().orElse(null);

        if (presupuesto != null) {
            // Si es un gasto, sumamos al gasto actual
            if (tipo == TipoTransaccion.GASTO) {
                presupuesto.setGastoActual(presupuesto.getGastoActual().add(monto));
            } else if (tipo == TipoTransaccion.INGRESO) {
                // Si es un ingreso, podrías manejar la lógica de cómo el ingreso afecta el presupuesto
            }
            presupuestoRepository.save(presupuesto);  // Guardamos los cambios en el presupuesto
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

        // Actualizar la cuenta si es necesario
        if (transaccionDTO.getCuentaId() != null) {
            Cuenta cuenta = cuentaRepository.findById(transaccionDTO.getCuentaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + transaccionDTO.getCuentaId()));
            transaccion.setCuenta(cuenta);
        }

        // Actualizar la categoría si es necesario
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


    // Obtener todas las transacciones con paginación
    public Page<TransaccionDTO> obtenerTodasLasTransacciones(Pageable pageable) {
        Page<Transaccion> transacciones = transaccionRepository.findAll(pageable);
        return transacciones.map(this::mapToDTO);
    }

    // Obtener transacciones por usuario con paginación
    public Page<TransaccionDTO> obtenerTransaccionesPorUsuarioId(Long usuarioId, Pageable pageable) {
        Page<Transaccion> transacciones = transaccionRepository.findByCuentaUsuarioId(usuarioId, pageable);
        return transacciones.map(this::mapToDTO);
    }

    private TransaccionDTO mapToDTO(Transaccion transaccion) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        transaccionDTO.setId(transaccion.getId());
        transaccionDTO.setMonto(transaccion.getMonto());
        transaccionDTO.setDestinatario(transaccion.getDestinatario());
        transaccionDTO.setFecha(transaccion.getFecha());
        transaccionDTO.setTipo(transaccion.getTipo());
        if (transaccion.getCuenta() != null) {
            transaccionDTO.setCuentaId(transaccion.getCuenta().getId());
        }
        if (transaccion.getCategoria() != null) {
            transaccionDTO.setCategoriaId(transaccion.getCategoria().getId());
        }
        return transaccionDTO;
    }
}
