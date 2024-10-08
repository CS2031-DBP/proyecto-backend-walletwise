package com.example.walletwise.Reporte.infrastructure;

import com.example.walletwise.Reporte.domain.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}
