package com.example.walletwise.Presupuesto.application;

import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
import com.example.walletwise.Presupuesto.domain.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PresupuestoDTO> crearPresupuesto(@RequestBody PresupuestoDTO presupuestoDTO) {
        return new ResponseEntity<>(presupuestoService.crearPresupuesto(presupuestoDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PresupuestoDTO>> listarTodosLosPresupuestos() {
        return ResponseEntity.ok(presupuestoService.obtenerTodosLosPresupuestos());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PresupuestoDTO>> obtenerPresupuestosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(presupuestoService.obtenerPresupuestosPorUsuarioId(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PresupuestoDTO> obtenerPresupuestoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(presupuestoService.obtenerPresupuestoPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PresupuestoDTO> actualizarPresupuesto(@PathVariable Long id, @RequestBody PresupuestoDTO presupuestoDTO) {
        return ResponseEntity.ok(presupuestoService.actualizarPresupuesto(id, presupuestoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPresupuesto(@PathVariable Long id) {
        presupuestoService.eliminarPresupuesto(id);
        return ResponseEntity.noContent().build();
    }
}