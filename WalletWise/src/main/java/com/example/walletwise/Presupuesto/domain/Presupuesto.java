package com.example.walletwise.Presupuesto.domain;

import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal montoTotal;

    @Column(nullable = false)
    private BigDecimal gastoActual;

    @Column
    private String alerta;

    @Column(nullable = false)
    private String periodo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


}
