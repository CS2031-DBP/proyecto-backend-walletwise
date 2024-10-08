package com.example.walletwise.dto;

public class SubcategoriaDTO {
    private Long id;
    private String nombre;       // Nombre de la subcategoría
    private Long categoriaId;    // Referencia a la categoría a la que pertenece

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}

