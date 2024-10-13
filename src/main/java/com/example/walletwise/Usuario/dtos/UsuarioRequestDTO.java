package com.example.walletwise.Usuario.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    private String nombre;
    private String email;
    private String password;
}