package com.example.walletwise.Presupuesto.domain;

import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    public PresupuestoDTO crearPresupuesto(PresupuestoDTO presupuestoDTO) {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setMontoTotal(presupuestoDTO.getMontoTotal());
        presupuesto.setGastoActual(presupuestoDTO.getGastoActual());
        presupuesto.setCategoria(presupuestoDTO.getCategoria());
        presupuesto.setAlerta(presupuestoDTO.getAlerta());
        presupuesto.setPeriodo(presupuestoDTO.getPeriodo());
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
        presupuesto.setCategoria(presupuestoDTO.getCategoria());
        presupuesto.setAlerta(presupuestoDTO.getAlerta());
        presupuesto.setPeriodo(presupuestoDTO.getPeriodo());

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
        presupuestoDTO.setCategoria(presupuesto.getCategoria());
        presupuestoDTO.setAlerta(presupuesto.getAlerta());
        presupuestoDTO.setPeriodo(presupuesto.getPeriodo());
        return presupuestoDTO;
    }
}
