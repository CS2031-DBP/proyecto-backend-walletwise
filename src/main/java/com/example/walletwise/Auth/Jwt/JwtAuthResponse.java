package com.example.walletwise.Auth.Jwt;

import com.example.walletwise.Usuario.domain.Role;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private Role role;
}