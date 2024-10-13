package com.example.walletwise.Presupuesto.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public PresupuestoDTO crearPresupuesto(PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setMontoTotal(presupuestoDTO.getMontoTotal());
        presupuesto.setGastoActual(presupuestoDTO.getGastoActual());
        presupuesto.setAlerta(presupuestoDTO.getAlerta());
        presupuesto.setPeriodo(presupuestoDTO.getPeriodo());

        // Asignar Usuario al Presupuesto
        Usuario usuario = usuarioRepository.findById(presupuestoDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + presupuestoDTO.getUsuarioId()));
        presupuesto.setUsuario(usuario);

        // Asignar Categoria al Presupuesto
        Categoria categoria = categoriaRepository.findById(presupuestoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + presupuestoDTO.getCategoriaId()));
        presupuesto.setCategoria(categoria);

        presupuestoRepository.save(presupuesto);
        return mapToDTO(presupuesto);
    }

    public List<PresupuestoDTO> obtenerTodosLosPresupuestos() {
        List<Presupuesto> presupuestos = presupuestoRepository.findAll();
        return presupuestos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PresupuestoDTO obtenerPresupuestoPorId(Long id) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));
        return mapToDTO(presupuesto);
    }

    public List<PresupuestoDTO> obtenerPresupuestosPorUsuarioId(Long usuarioId) {
        List<Presupuesto> presupuestos = presupuestoRepository.findByUsuarioId(usuarioId);
        return presupuestos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PresupuestoDTO actualizarPresupuesto(Long id, PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));

        presupuesto.setMontoTotal(presupuestoDTO.getMontoTotal());
        presupuesto.setGastoActual(presupuestoDTO.getGastoActual());
        presupuesto.setAlerta(presupuestoDTO.getAlerta());
        presupuesto.setPeriodo(presupuestoDTO.getPeriodo());

        // Actualizar Usuario si es necesario
        if (presupuestoDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(presupuestoDTO.getUsuarioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + presupuestoDTO.getUsuarioId()));
            presupuesto.setUsuario(usuario);
        }

        // Actualizar Categoria si es necesario
        if (presupuestoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(presupuestoDTO.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + presupuestoDTO.getCategoriaId()));
            presupuesto.setCategoria(categoria);
        }

        presupuestoRepository.save(presupuesto);
        return mapToDTO(presupuesto);
    }

    public void eliminarPresupuesto(Long id) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));
        presupuestoRepository.delete(presupuesto);
    }

    private PresupuestoDTO mapToDTO(Presupuesto presupuesto) {
        PresupuestoDTO presupuestoDTO = new PresupuestoDTO();
        presupuestoDTO.setId(presupuesto.getId());
        presupuestoDTO.setMontoTotal(presupuesto.getMontoTotal());
        presupuestoDTO.setGastoActual(presupuesto.getGastoActual());
        presupuestoDTO.setAlerta(presupuesto.getAlerta());
        presupuestoDTO.setPeriodo(presupuesto.getPeriodo());
        presupuestoDTO.setUsuarioId(presupuesto.getUsuario().getId());
        presupuestoDTO.setCategoriaId(presupuesto.getCategoria().getId());
        return presupuestoDTO;
    }
}

