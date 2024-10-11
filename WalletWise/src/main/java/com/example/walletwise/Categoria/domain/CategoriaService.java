package com.example.walletwise.Categoria.domain;

import com.example.walletwise.Categoria.dtos.CategoriaDTO;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    // Listar todas las Categorías
    public List<CategoriaDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Obtener una Categoría por ID
    public CategoriaDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        return mapToDTO(categoria);
    }

    // Actualizar una Categoría existente
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setTipo(categoriaDTO.getTipo());
        categoriaRepository.save(categoria);
        return mapToDTO(categoria);
    }

    // Eliminar una Categoría
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
        categoriaRepository.delete(categoria);
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

