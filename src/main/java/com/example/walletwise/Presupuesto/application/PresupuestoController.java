package com.example.walletwise.Presupuesto.application;

import com.example.walletwise.Presupuesto.dtos.CrearPresupuestoDTO;
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
    public ResponseEntity<PresupuestoDTO> crearPresupuesto(@RequestBody CrearPresupuestoDTO presupuestoDTO) {
        return new ResponseEntity<>(presupuestoService.crearPresupuesto(presupuestoDTO), HttpStatus.CREATED);
    }


    @GetMapping("/mispresupuestos")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<PresupuestoDTO>> obtenerMisPresupuestos() {
        return ResponseEntity.ok(presupuestoService.obtenerPresupuestosUsuarioAutenticado());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PresupuestoDTO> obtenerPresupuestoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(presupuestoService.obtenerPresupuestoPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PresupuestoDTO> actualizarPresupuesto(
            @PathVariable Long id,
            @RequestBody CrearPresupuestoDTO presupuestoDTO) {
        return ResponseEntity.ok(presupuestoService.actualizarPresupuesto(id, presupuestoDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> eliminarPresupuesto(@PathVariable Long id) {
        presupuestoService.eliminarPresupuesto(id);
        return ResponseEntity.noContent().build();
    }
}