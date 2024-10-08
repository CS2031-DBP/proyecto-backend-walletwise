package com.example.walletwise.Transaccion.application;

import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.domain.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    public ResponseEntity<TransaccionDTO> crearTransaccion(@RequestBody TransaccionDTO transaccionDTO) {
        return new ResponseEntity<>(transaccionService.crearTransaccion(transaccionDTO), HttpStatus.CREATED);
    }
}

