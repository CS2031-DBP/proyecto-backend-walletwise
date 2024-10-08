package com.example.walletwise.Subcategoria.infrastructure;

import com.example.walletwise.Subcategoria.domain.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
}
