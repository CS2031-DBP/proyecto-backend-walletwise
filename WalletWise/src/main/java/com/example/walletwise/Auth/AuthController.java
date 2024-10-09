package com.example.walletwise.Auth;

import com.example.walletwise.Auth.dtos.JwtAuthResponse;
import com.example.walletwise.Auth.dtos.LoginReq;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.dtos.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq loginReq) {
        JwtAuthResponse response = authService.login(loginReq);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        JwtAuthResponse response = authService.register(req);
        return ResponseEntity.ok(response);
    }
}
