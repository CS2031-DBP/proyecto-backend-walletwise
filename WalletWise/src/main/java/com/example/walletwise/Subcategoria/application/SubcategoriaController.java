package com.example.walletwise.Subcategoria.application;

import com.example.walletwise.Subcategoria.dtos.SubcategoriaDTO;
import com.example.walletwise.Subcategoria.domain.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @PostMapping
    public ResponseEntity<SubcategoriaDTO> crearSubcategoria(@RequestBody SubcategoriaDTO subcategoriaDTO) {
        return new ResponseEntity<>(subcategoriaService.crearSubcategoria(subcategoriaDTO), HttpStatus.CREATED);
    }
}

