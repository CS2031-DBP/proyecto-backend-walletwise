package com.example.walletwise.Subcategoria.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SubcategoriaDTO {
    private Long id;

    private String nombre;       // Nombre de la subcategoría

    private Long categoriaId; // Referencia a la categoría a la que pertenece

    private String descripcion;

    private String categoriaNombre; // Nuevo campo para el nombre de la categoría

}

