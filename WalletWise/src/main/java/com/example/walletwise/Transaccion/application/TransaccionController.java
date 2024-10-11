package com.example.walletwise.Transaccion.application;

import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.domain.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TransaccionDTO> crearTransaccion(@Valid @RequestBody TransaccionDTO transaccionDTO) {
        return new ResponseEntity<>(transaccionService.crearTransaccion(transaccionDTO), HttpStatus.CREATED);
    }

    // Listar todas las Transacciones (Solo ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransaccionDTO>> listarTodasLasTransacciones() {
        return ResponseEntity.ok(transaccionService.obtenerTodasLasTransacciones());
    }

    // Obtener Transacciones por Usuario
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TransaccionDTO>> obtenerTransaccionesPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionesPorUsuarioId(usuarioId));
    }

    // Obtener una Transacción por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TransaccionDTO> obtenerTransaccionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionPorId(id));
    }

    // Actualizar una Transacción
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<TransaccionDTO> actualizarTransaccion(@PathVariable Long id, @Valid @RequestBody TransaccionDTO transaccionDTO) {
        return ResponseEntity.ok(transaccionService.actualizarTransaccion(id, transaccionDTO));
    }

    // Eliminar una Transacción (Solo ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id) {
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.noContent().build();
    }
}


