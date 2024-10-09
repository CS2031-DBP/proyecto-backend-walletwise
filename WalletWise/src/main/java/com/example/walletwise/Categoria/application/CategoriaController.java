package com.example.walletwise.Categoria.application;

import com.example.walletwise.Categoria.dtos.CategoriaDTO;
import com.example.walletwise.Categoria.domain.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @PreAuthorize("hasRole('USER')") // Define el rol requerido
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO creado = categoriaService.crearCategoria(categoriaDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

}

