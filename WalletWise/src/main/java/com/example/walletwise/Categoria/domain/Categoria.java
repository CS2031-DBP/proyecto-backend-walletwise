package com.example.walletwise.Categoria.domain;

import com.example.walletwise.Subcategoria.domain.Subcategoria;
import com.example.walletwise.Transaccion.domain.Transaccion;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCategoria tipo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Subcategoria> subcategorias;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;

}

