package com.example.walletwise.Cuenta.domain;

import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.Usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String banco;

    @Column(nullable = false)
    private BigDecimal saldo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta; // Enum aplicado

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Moneda moneda; // Enum aplicado

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Transaccion> transacciones;

}
