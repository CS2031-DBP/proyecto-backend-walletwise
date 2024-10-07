package com.example.walletwise.Usuario.infrastructure;

import com.example.walletwise.Usuario.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
