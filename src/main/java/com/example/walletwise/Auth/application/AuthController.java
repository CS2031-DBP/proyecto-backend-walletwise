package com.example.walletwise.Auth.application;

import com.example.walletwise.Auth.domain.AuthService;
import com.example.walletwise.Auth.Jwt.JwtAuthResponse;
import com.example.walletwise.Auth.dtos.LoginReq;
import com.example.walletwise.Usuario.dtos.RegisterReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        logger.info("Intento de registro para el email: {}", req.getEmail());
        try {
            JwtAuthResponse response = authService.register(req);
            logger.info("Registro exitoso para el email: {}", req.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error durante el registro para el email: {}", req.getEmail(), e);
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq loginReq) {
        logger.info("Intento de login para el email: {}", loginReq.getEmail());
        try {
            JwtAuthResponse response = authService.login(loginReq);
            logger.info("Login exitoso para el email: {}", loginReq.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error durante el login para el email: {}", loginReq.getEmail(), e);
            throw e;
        }
    }
}
