package com.example.walletwise.Subcategoria.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Subcategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}
