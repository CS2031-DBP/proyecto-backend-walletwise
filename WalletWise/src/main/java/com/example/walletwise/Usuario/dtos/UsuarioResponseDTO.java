package com.example.walletwise.Usuario.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaRegistro;
}
