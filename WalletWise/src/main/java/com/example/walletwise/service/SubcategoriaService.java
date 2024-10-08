package com.example.walletwise.service;

import com.example.walletwise.domain.Subcategoria;
import com.example.walletwise.dto.SubcategoriaDTO;
import com.example.walletwise.infrastructure.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    public SubcategoriaDTO crearSubcategoria(SubcategoriaDTO subcategoriaDTO) {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(subcategoriaDTO.getNombre());
        subcategoriaRepository.save(subcategoria);
        return mapToDTO(subcategoria);
    }

    private SubcategoriaDTO mapToDTO(Subcategoria subcategoria) {
        SubcategoriaDTO subcategoriaDTO = new SubcategoriaDTO();
        subcategoriaDTO.setId(subcategoria.getId());
        subcategoriaDTO.setNombre(subcategoria.getNombre());
        return subcategoriaDTO;
    }
}

