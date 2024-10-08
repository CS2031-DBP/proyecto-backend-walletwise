package com.example.walletwise.Presupuesto.domain;

import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.infrastructure.PresupuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
