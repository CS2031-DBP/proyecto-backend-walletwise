package com.example.walletwise.Categoria.dtos;

import com.example.walletwise.Categoria.domain.TipoCategoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaDTO {
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    private String nombre;       // Nombre de la categoría, como "Alimentación"

    private String descripcion;  // Descripción opcional de la categoría

    @NotNull(message = "El tipo de categoría es obligatorio")
    private TipoCategoria tipo;  // Puede ser "INGRESO" o "GASTO"
}

