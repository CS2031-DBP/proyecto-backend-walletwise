package com.example.walletwise.Auth.domain;

import com.example.walletwise.Auth.Jwt.JwtService;
import com.example.walletwise.Auth.Jwt.JwtAuthResponse;
import com.example.walletwise.Auth.dtos.LoginReq;
import com.example.walletwise.Usuario.domain.Role;
import com.example.walletwise.Usuario.domain.Usuario;
import com.example.walletwise.Usuario.dtos.RegisterReq;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.UserAlreadyExistException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private ModelMapper modelMapper = new ModelMapper();

    public JwtAuthResponse login(LoginReq req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + req.getEmail()));

        String token = jwtService.generateToken(usuario);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return response;
    }

    public JwtAuthResponse register(RegisterReq req) {
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(req.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(req.getFirstName() + " " + req.getLastName());
        usuario.setEmail(req.getEmail());
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));
        usuario.setFechaRegistro(LocalDateTime.now().toLocalDate());

        // Asigna el rol de "USER" por defecto
        usuario.setRole(Role.USER);

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario);
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return response;
    }
}