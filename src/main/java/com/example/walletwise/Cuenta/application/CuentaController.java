package com.example.walletwise.Cuenta.application;

import com.example.walletwise.Cuenta.dtos.CuentaDTO;
import com.example.walletwise.Cuenta.domain.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CuentaDTO> crearCuenta(@RequestBody CuentaDTO cuentaDTO) {
        return new ResponseEntity<>(cuentaService.crearCuenta(cuentaDTO), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CuentaDTO>> listarTodasLasCuentas() {
        return ResponseEntity.ok(cuentaService.obtenerTodasLasCuentas());
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<CuentaDTO>> obtenerCuentasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.obtenerCuentasPorUsuarioId(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CuentaDTO> obtenerCuentaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CuentaDTO> actualizarCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, cuentaDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

