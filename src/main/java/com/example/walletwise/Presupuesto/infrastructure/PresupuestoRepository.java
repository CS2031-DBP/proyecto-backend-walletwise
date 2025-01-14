package com.example.walletwise.Presupuesto.infrastructure;

import com.example.walletwise.Presupuesto.domain.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
    List<Presupuesto> findByUsuarioId(Long usuarioId);
    List<Presupuesto> findByUsuarioIdAndCategoriaId(Long usuarioId, Long categoriaId);
}
