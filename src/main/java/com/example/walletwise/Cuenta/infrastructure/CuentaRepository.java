package com.example.walletwise.Cuenta.infrastructure;

import com.example.walletwise.Cuenta.domain.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
