package com.example.walletwise;

import com.example.walletwise.Usuario.domain.Role;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica si ya existe un usuario admin
        if (usuarioRepository.findByEmail("admin@example.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("adminPassword123"));
            admin.setRole(Role.ADMIN);
            admin.setFechaRegistro(java.time.LocalDate.now());

            usuarioRepository.save(admin);
            System.out.println("Usuario Admin creado");
        }
    }
}