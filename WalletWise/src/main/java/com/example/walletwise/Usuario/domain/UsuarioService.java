package com.example.walletwise.Usuario.domain;

import com.example.walletwise.Usuario.dtos.UsuarioDTO;
import com.example.walletwise.Usuario.dtos.UsuarioRequestDTO;
import com.example.walletwise.Usuario.dtos.UsuarioResponseDTO;
import com.example.walletwise.Usuario.infrastructure.UsuarioRepository;
import com.example.walletwise.exceptions.ResourceNotFoundException;
import com.example.walletwise.exceptions.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.findByEmail(usuarioRequestDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
        usuario.setFechaRegistro(java.time.LocalDate.now());
        usuario.setRole(Role.USER);

        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO crearAdmin(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.findByEmail(usuarioRequestDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
        usuario.setFechaRegistro(java.time.LocalDate.now());
        usuario.setRole(Role.ADMIN);

        usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public UsuarioResponseDTO obtenerUsuarioPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(usuarioRequestDTO.getNombre());
        usuario.setEmail(usuarioRequestDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequestDTO.getPassword()));
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        usuarioRepository.delete(usuario);
    }
}
