package com.example.walletwise.Subcategoria.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.Subcategoria.dtos.SubcategoriaDTO;
import com.example.walletwise.Subcategoria.infrastructure.SubcategoriaRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public SubcategoriaDTO crearSubcategoria(SubcategoriaDTO subcategoriaDTO) {
        Subcategoria subcategoria = new Subcategoria();
        subcategoria.setNombre(subcategoriaDTO.getNombre());
        subcategoria.setDescripcion(subcategoriaDTO.getDescripcion());

        // Asignar la categoría a la subcategoría
        Categoria categoria = categoriaRepository.findById(subcategoriaDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        subcategoria.setCategoria(categoria);

        subcategoriaRepository.save(subcategoria);
        return mapToDTO(subcategoria);
    }

    public List<SubcategoriaDTO> obtenerTodasSubcategorias() {
        List<Subcategoria> subcategorias = subcategoriaRepository.findAll();
        return subcategorias.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public SubcategoriaDTO obtenerSubcategoriaPorId(Long id) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada"));
        return mapToDTO(subcategoria);
    }

    public SubcategoriaDTO actualizarSubcategoria(Long id, SubcategoriaDTO subcategoriaDTO) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada"));

        subcategoria.setNombre(subcategoriaDTO.getNombre());
        subcategoria.setDescripcion(subcategoriaDTO.getDescripcion());

        // Actualizar la categoría si es necesario
        if (subcategoriaDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(subcategoriaDTO.getCategoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
            subcategoria.setCategoria(categoria);
        }

        subcategoriaRepository.save(subcategoria);
        return mapToDTO(subcategoria);
    }

    public void eliminarSubcategoria(Long id) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategoría no encontrada"));
        subcategoriaRepository.delete(subcategoria);
    }

    private SubcategoriaDTO mapToDTO(Subcategoria subcategoria) {
        SubcategoriaDTO subcategoriaDTO = new SubcategoriaDTO();
        subcategoriaDTO.setId(subcategoria.getId());
        subcategoriaDTO.setNombre(subcategoria.getNombre());
        return subcategoriaDTO;
    }
}

