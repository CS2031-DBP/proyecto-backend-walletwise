package com.example.walletwise.Transaccion.infrastructure;

import com.example.walletwise.Transaccion.domain.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
