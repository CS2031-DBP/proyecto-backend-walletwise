package com.example.walletwise.Categoria.dtos;

public class CategoriaDTO {
    private Long id;
    private String nombre;       // Nombre de la categoría, como "Alimentación"
    private String descripcion;  // Descripción opcional de la categoría
    private String tipo;         // Puede ser "Ingreso" o "Gasto"

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

