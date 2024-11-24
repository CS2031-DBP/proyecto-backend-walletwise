// JwtAuthResponse.java
package com.example.walletwise.Auth.Jwt;

import com.example.walletwise.Usuario.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
    private Long id;
    private String nombre;
    private String email;
    private Role role;
}