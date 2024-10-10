package com.example.walletwise.Categoria.dtos;

import com.example.walletwise.Categoria.domain.TipoCategoria;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO {
    private Long id;
    private String nombre;       // Nombre de la categoría, como "Alimentación"
    private String descripcion;  // Descripción opcional de la categoría
    private TipoCategoria tipo;       // Puede ser "Ingreso" o "Gasto"
}

