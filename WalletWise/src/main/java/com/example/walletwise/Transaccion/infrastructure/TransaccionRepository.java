package com.example.walletwise.Transaccion.infrastructure;

import com.example.walletwise.Transaccion.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
