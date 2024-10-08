package com.example.walletwise.service;

import com.example.walletwise.domain.Categoria;
import com.example.walletwise.dto.CategoriaDTO;
import com.example.walletwise.infrastructure.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setTipo(categoriaDTO.getTipo());
        categoriaRepository.save(categoria);
        return mapToDTO(categoria);
    }

    private CategoriaDTO mapToDTO(Categoria categoria) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setTipo(categoria.getTipo());
        return categoriaDTO;
    }
}

