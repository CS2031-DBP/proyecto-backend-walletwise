package com.example.walletwise.Subcategoria.application;

import com.example.walletwise.Subcategoria.dtos.SubcategoriaDTO;
import com.example.walletwise.Subcategoria.domain.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SubcategoriaDTO> crearSubcategoria(@RequestBody SubcategoriaDTO subcategoriaDTO) {
        return new ResponseEntity<>(subcategoriaService.crearSubcategoria(subcategoriaDTO), HttpStatus.CREATED);
    }
    // Obtener todas las subcategorías
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubcategoriaDTO>> obtenerTodasSubcategorias() {
        List<SubcategoriaDTO> subcategorias = subcategoriaService.obtenerTodasSubcategorias();
        return new ResponseEntity<>(subcategorias, HttpStatus.OK);
    }

    // Obtener una subcategoría por su ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SubcategoriaDTO> obtenerSubcategoriaPorId(@PathVariable Long id) {
        SubcategoriaDTO subcategoriaDTO = subcategoriaService.obtenerSubcategoriaPorId(id);
        return new ResponseEntity<>(subcategoriaDTO, HttpStatus.OK);
    }

    // Actualizar una subcategoría
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SubcategoriaDTO> actualizarSubcategoria(@PathVariable Long id, @RequestBody SubcategoriaDTO subcategoriaDTO) {
        SubcategoriaDTO actualizada = subcategoriaService.actualizarSubcategoria(id, subcategoriaDTO);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    // Eliminar una subcategoría
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarSubcategoria(@PathVariable Long id) {
        subcategoriaService.eliminarSubcategoria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

