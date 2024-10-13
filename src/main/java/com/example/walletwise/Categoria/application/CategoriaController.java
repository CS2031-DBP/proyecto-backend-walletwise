package com.example.walletwise.Categoria.application;

import com.example.walletwise.Categoria.dtos.CategoriaDTO;
import com.example.walletwise.Categoria.domain.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // 1. Crear una nueva Categoría
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO creado = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // 2. Listar todas las Categorías
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    // 3. Obtener una Categoría por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoriaDTO> obtenerCategoriaPorId(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    // 4. Actualizar una Categoría existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@PathVariable Long id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO actualizado = categoriaService.actualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(actualizado);
    }

    // 5. Eliminar una Categoría
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
