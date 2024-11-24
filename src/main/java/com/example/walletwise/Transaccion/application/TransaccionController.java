package com.example.walletwise.Transaccion.application;

import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.example.walletwise.Transaccion.domain.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> listarTodasLasTransacciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<TransaccionDTO> transacciones = transaccionService.obtenerTodasLasTransacciones(pageable);

        // Crear un mapa para estructurar la respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("transacciones", transacciones.getContent()); // Lista de transacciones
        response.put("currentPage", transacciones.getNumber());   // Página actual
        response.put("totalItems", transacciones.getTotalElements()); // Total de elementos
        response.put("totalPages", transacciones.getTotalPages()); // Total de páginas

        return ResponseEntity.ok(response);
    }

    @GetMapping("/mistransacciones")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> obtenerMisTransacciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha") String sort
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<TransaccionDTO> transacciones = transaccionService.obtenerTransaccionesUsuarioAutenticado(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("transacciones", transacciones.getContent());
        response.put("currentPage", transacciones.getNumber());
        response.put("totalItems", transacciones.getTotalElements());
        response.put("totalPages", transacciones.getTotalPages());

        return ResponseEntity.ok(response);
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


