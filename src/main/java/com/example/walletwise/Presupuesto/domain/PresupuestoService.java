package com.example.walletwise.Presupuesto.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Presupuesto.dtos.CrearPresupuestoDTO;
import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public PresupuestoDTO crearPresupuesto(CrearPresupuestoDTO crearPresupuestoDTO) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setMontoTotal(crearPresupuestoDTO.getMontoTotal());
        presupuesto.setGastoActual(crearPresupuestoDTO.getGastoActual());
        presupuesto.setAlerta(crearPresupuestoDTO.getAlerta());
        presupuesto.setPeriodo(crearPresupuestoDTO.getPeriodo());
        presupuesto.setUsuario(usuario);

        // Asignar Categoria al Presupuesto
        Categoria categoria = categoriaRepository.findById(crearPresupuestoDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + crearPresupuestoDTO.getCategoriaId()));
        presupuesto.setCategoria(categoria);

        presupuestoRepository.save(presupuesto);
        return mapToDTO(presupuesto);
    }

    public List<PresupuestoDTO> obtenerPresupuestosUsuarioAutenticado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Presupuesto> presupuestos = presupuestoRepository.findByUsuarioId(usuario.getId());
        return presupuestos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PresupuestoDTO actualizarPresupuesto(Long id, CrearPresupuestoDTO presupuestoDTO) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));

        // Verificar que el presupuesto pertenece al usuario autenticado
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para modificar este presupuesto");
        }

        presupuesto.setMontoTotal(presupuestoDTO.getMontoTotal());
        presupuesto.setGastoActual(presupuestoDTO.getGastoActual());
        presupuesto.setAlerta(presupuestoDTO.getAlerta());
        presupuesto.setPeriodo(presupuestoDTO.getPeriodo());

        // Actualizar Categoria si es necesario
        if (presupuestoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(presupuestoDTO.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + presupuestoDTO.getCategoriaId()));
            presupuesto.setCategoria(categoria);
        }

        presupuestoRepository.save(presupuesto);
        return mapToDTO(presupuesto);
    }

    public PresupuestoDTO obtenerPresupuestoPorId(Long id) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));

        // Verificar que el presupuesto pertenece al usuario autenticado
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para ver este presupuesto");
        }

        return mapToDTO(presupuesto);
    }

    public void eliminarPresupuesto(Long id) {
        // Obtener el usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Presupuesto no encontrado con id: " + id));

        // Verificar que el presupuesto pertenece al usuario autenticado
        if (!presupuesto.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este presupuesto");
        }

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


