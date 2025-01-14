package com.example.walletwise.Usuario.infrastructure;

import com.example.walletwise.Usuario.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    // Método para obtener usuarios con paginación
    Page<Usuario> findAll(Pageable pageable);
}
