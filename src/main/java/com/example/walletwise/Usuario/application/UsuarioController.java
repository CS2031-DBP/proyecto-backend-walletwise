package com.example.walletwise.Usuario.application;

import com.example.walletwise.Usuario.dtos.UsuarioDTO;
import com.example.walletwise.Usuario.domain.UsuarioService;
import com.example.walletwise.Usuario.dtos.UsuarioRequestDTO;
import com.example.walletwise.Usuario.dtos.UsuarioResponseDTO;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioCreado = usuarioService.crearUsuario(usuarioRequestDTO);
        return ResponseEntity.ok(usuarioCreado);
    }

    @PostMapping("/admin/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> crearAdmin(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO adminCreado = usuarioService.crearAdmin(usuarioRequestDTO);
        return ResponseEntity.ok(adminCreado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorEmail(@PathVariable String email) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> listarUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("usuarios", usuarios.getContent());
        response.put("currentPage", usuarios.getNumber());
        response.put("totalItems", usuarios.getTotalElements());
        response.put("totalPages", usuarios.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioRequestDTO);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
