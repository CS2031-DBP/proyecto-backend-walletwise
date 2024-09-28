package com.example.walletwise.User.infrastructure;

import com.example.walletwise.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
