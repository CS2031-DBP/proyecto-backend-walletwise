package com.example.walletwise.controller;

import com.example.walletwise.dto.PresupuestoDTO;
import com.example.walletwise.service.PresupuestoService;
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

