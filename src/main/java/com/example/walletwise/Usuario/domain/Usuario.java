package com.example.walletwise.Usuario.domain;

import com.example.walletwise.Cuenta.domain.Cuenta;
import com.example.walletwise.Presupuesto.domain.Presupuesto;
import com.example.walletwise.Reporte.domain.Reporte;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Cuenta> cuentas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Presupuesto> presupuestos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reporte> reportes;

}
