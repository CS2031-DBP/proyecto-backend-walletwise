package com.example.walletwise.Presupuesto.infrastructure;

import com.example.walletwise.Presupuesto.domain.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {
}
