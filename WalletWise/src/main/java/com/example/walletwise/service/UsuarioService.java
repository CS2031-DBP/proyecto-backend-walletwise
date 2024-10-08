package com.example.walletwise.service;

import com.example.walletwise.domain.Usuario;
import com.example.walletwise.dto.UsuarioDTO;
import com.example.walletwise.infrastructure.UsuarioRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new BadRequestException("El email ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());  // Aquí puedes cifrar la contraseña antes de guardarla
        usuario.setFechaRegistro(LocalDate.now());

        usuarioRepository.save(usuario);
        return mapToDTO(usuario);
    }

    private UsuarioDTO mapToDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        return usuarioDTO;  // El password no se devuelve en las respuestas para mayor seguridad
    }
}
