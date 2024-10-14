package com.example.walletwise.Reporte.domain;

import com.example.walletwise.Transaccion.domain.Transaccion;
import com.example.walletwise.Usuario.domain.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoReporte tipoReporte; // Enum aplicado

    @Column
    private String contenido;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String formato; // "PDF", "CSV", etc.

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id")
    private List<Transaccion> transacciones;

}
