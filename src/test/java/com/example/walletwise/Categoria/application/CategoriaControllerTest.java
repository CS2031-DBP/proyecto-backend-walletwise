/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Categoria.application;

import com.example.walletwise.Categoria.domain.CategoriaService;
import com.example.walletwise.Categoria.domain.TipoCategoria;
import com.example.walletwise.Categoria.dtos.CategoriaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoriaDTO categoriaDTO;
    private CategoriaDTO categoriaDTOUpdate;

    private List<CategoriaDTO> listCategoriaDTO;

    @BeforeEach
    public void setUp() {
        listCategoriaDTO = new ArrayList();
        categoriaDTOUpdate = new CategoriaDTO();
        categoriaDTO = new CategoriaDTO();

        categoriaDTO.setId(1L);
        categoriaDTO.setNombre("Salario");
        categoriaDTO.setDescripcion("Salario recibido mensualmente");
        categoriaDTO.setTipo(TipoCategoria.INGRESO);

        categoriaDTOUpdate.setId(1L);
        categoriaDTOUpdate.setNombre("Salario");
        categoriaDTOUpdate.setDescripcion("Salario mensual");
        categoriaDTOUpdate.setTipo(TipoCategoria.INGRESO);

    }

    @WithMockUser(username = "user01")
    @Test
    public void testCreaCategoria_WhenUserEnabled() throws Exception {
        System.out.println("testCreaCategoria_WhenUserEnabled");

        when(categoriaService.crearCategoria(any())).thenReturn(categoriaDTO);

        ResultActions response = this.mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testCreaCategoria_UnAuthorized() throws Exception {
        System.out.println("testCreaCategoria_UnAuthorized");

        when(categoriaService.crearCategoria(any())).thenReturn(categoriaDTO);

        ResultActions response = this.mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarCategorias() throws Exception {
        System.out.println("testListarCategorias");

        listCategoriaDTO.add(categoriaDTO);

        when(categoriaService.obtenerTodasLasCategorias()).thenReturn(listCategoriaDTO);

        ResultActions response = this.mockMvc.perform(get("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listCategoriaDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testListarCategorias_UnAuthorized() throws Exception {
        System.out.println("testListarCategorias_UnAuthorized");

        listCategoriaDTO.add(categoriaDTO);

        when(categoriaService.obtenerTodasLasCategorias()).thenReturn(listCategoriaDTO);

        ResultActions response = this.mockMvc.perform(get("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testObtenerCategoriaPorId() throws Exception {
        System.out.println("testObtenerCategoriaPorId");
        Long id = 1L;
        when(categoriaService.obtenerCategoriaPorId(any())).thenReturn(categoriaDTO);

        ResultActions response = this.mockMvc.perform(get("/api/categorias/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(categoriaDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testActualizarCategoria() throws Exception {
        System.out.println("testActualizarCategoria");

        Long id = 1L;
        categoriaDTO.setDescripcion("Salario mensual");
        when(categoriaService.actualizarCategoria(any(), any())).thenReturn(categoriaDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/categorias/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoriaDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion", CoreMatchers.is(categoriaDTOUpdate.getDescripcion())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarCategoria() throws Exception {

        System.out.println("testEliminarCategoria");

        Long id = 1L;
        doNothing().when(categoriaService).eliminarCategoria(ArgumentMatchers.any());

        ResultActions response = this.mockMvc.perform(delete("/api/categorias/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
}
