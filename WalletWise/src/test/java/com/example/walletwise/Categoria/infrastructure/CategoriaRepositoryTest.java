/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Categoria.infrastructure;

import com.example.walletwise.AbstractContainerBaseTest;
import com.example.walletwise.Categoria.domain.Categoria;
import com.example.walletwise.Categoria.domain.TipoCategoria;
import com.example.walletwise.Categoria.infrastructure.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoriaRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    CategoriaRepository repository;

    @Autowired
    TestEntityManager entityManager;

    Categoria categoria;
    Categoria categoria2;

    @BeforeEach
    public void setUp() {

        categoria = new Categoria();

        categoria.setNombre("Salario");
        categoria.setDescripcion("Salario recibido mensualmente");
        categoria.setTipo(TipoCategoria.INGRESO);

        entityManager.persist(categoria);
        entityManager.flush();

    }

    @Test
    void shouldCreateCategoria() {
        System.out.println("shouldCreateCategoria");

        categoria2 = new Categoria();

        categoria2.setNombre("Gastos Educativos");
        categoria2.setDescripcion("Pago mensual por derechos educativos");
        categoria2.setTipo(TipoCategoria.GASTO);

        entityManager.persist(categoria2);
        entityManager.flush();

        Optional<Categoria> categoriaReturned = repository.findById(categoria2.getId());

        assertNotNull(categoriaReturned);
        assertTrue(repository.findById(categoria2.getId()).isPresent());

    }

    @Test
    void shouldFindByIdCategoria() {
        System.out.println("shouldFindByIdCategoria");
        Optional<Categoria> categoriaReturned = repository.findById(categoria.getId());

        assertNotNull(categoriaReturned);
        assertTrue(repository.findById(categoria.getId()).isPresent());

    }

    @Test
    void shouldDeleteById() {
        System.out.println("shouldDeleteById");

        repository.deleteById(categoria.getId());

        assertTrue(repository.findById(categoria.getId()).isEmpty());
    }

    @Test
    void shouldNotDeleteCategoriaDontExist() {
        System.out.println("shouldNotDeleteCategoriaDontExist");
        long categoriaIdNotExist = 10L;

        assertTrue(repository.findById(categoriaIdNotExist).isEmpty());

        repository.deleteById(categoriaIdNotExist);

    }

}
