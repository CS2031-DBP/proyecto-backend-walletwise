package com.example.walletwise.Cuenta.application;

import com.example.walletwise.Cuenta.dtos.CuentaDTO;
import com.example.walletwise.Cuenta.domain.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return new ResponseEntity<>(cuentaService.crearCuenta(cuentaDTO), HttpStatus.CREATED);
    }
}

