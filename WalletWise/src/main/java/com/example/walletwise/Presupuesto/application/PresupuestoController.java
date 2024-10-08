package com.example.walletwise.Presupuesto.application;

import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.domain.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    @PostMapping
    public ResponseEntity<PresupuestoDTO> crearPresupuesto(@RequestBody PresupuestoDTO presupuestoDTO) {
        return new ResponseEntity<>(presupuestoService.crearPresupuesto(presupuestoDTO), HttpStatus.CREATED);
    }
}

