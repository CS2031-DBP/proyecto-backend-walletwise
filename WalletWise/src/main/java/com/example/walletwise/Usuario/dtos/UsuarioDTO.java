package com.example.walletwise.Usuario.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private LocalDate fechaRegistro;
    private String password;   // Agrega el campo 'password'

}
