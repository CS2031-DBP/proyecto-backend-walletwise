package com.example.walletwise.Transaccion.infrastructure;

import com.example.walletwise.Transaccion.domain.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findByCuentaUsuarioId(Long usuarioId);
    List<Transaccion> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);

    // Método con paginación
    Page<Transaccion> findByCuentaUsuarioId(Long usuarioId, Pageable pageable);

    Page<Transaccion> findAll(Pageable pageable); // Paginación para todas las transacciones
}

