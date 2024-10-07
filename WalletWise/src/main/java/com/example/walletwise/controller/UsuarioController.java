package com.example.walletwise.controller;

import com.example.walletwise.dto.UsuarioDTO;
import com.example.walletwise.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuarioDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.obtenerUsuarioPorId(id), HttpStatus.OK);
    }
}

