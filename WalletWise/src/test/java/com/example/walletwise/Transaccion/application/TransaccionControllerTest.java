/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Transaccion.application;


import com.example.walletwise.Transaccion.domain.TipoTransaccion;
import com.example.walletwise.Transaccion.domain.TransaccionService;
import com.example.walletwise.Transaccion.dtos.TransaccionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransaccionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TransaccionService transaccionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransaccionDTO transaccionDTO;
    private TransaccionDTO transaccionDTOUpdate;

    private List<TransaccionDTO> listTransaccionDTO;
    private Long usuarioId;
    
    @BeforeEach
    public void setUp() {
        usuarioId = 1L;
        listTransaccionDTO = new ArrayList();
        transaccionDTOUpdate = new TransaccionDTO();
        transaccionDTO = new TransaccionDTO();

        transaccionDTO.setId(1L);
        transaccionDTO.setCategoriaId(1L);
        
        transaccionDTO.setDestinatario("Fulano de tal");
        transaccionDTO.setFecha(LocalDate.now());
        transaccionDTO.setMonto(new BigDecimal(400));
        transaccionDTO.setTipo(TipoTransaccion.GASTO);

        transaccionDTOUpdate.setId(1L);
        transaccionDTOUpdate.setCategoriaId(1L);
        
        transaccionDTOUpdate.setDestinatario("Fulano de tal");
        transaccionDTOUpdate.setFecha(LocalDate.now());
        transaccionDTOUpdate.setMonto(new BigDecimal(200));
        transaccionDTOUpdate.setTipo(TipoTransaccion.GASTO);

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testCrearTransaccion() throws Exception {
        System.out.println("testCrearTransaccion");

        when(transaccionService.crearTransaccion(any())).thenReturn(transaccionDTO);

        ResultActions response = this.mockMvc.perform(post("/api/transacciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaccionDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarTodasLasTransaccions() throws Exception {
        System.out.println("testListarTransaccions");

        listTransaccionDTO.add(transaccionDTO);

        when(transaccionService.obtenerTodasLasTransacciones()).thenReturn(listTransaccionDTO);

        ResultActions response = this.mockMvc.perform(get("/api/transacciones")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listTransaccionDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testObtenerTransaccionsPorUsuario() throws Exception {
        System.out.println("testObtenerTransaccionsPorUsuario");

        listTransaccionDTO.add(transaccionDTO);
        when(transaccionService.obtenerTransaccionesPorUsuarioId(any())).thenReturn(listTransaccionDTO);

        ResultActions response = this.mockMvc.perform(get("/api/transacciones/usuario/".concat(usuarioId.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listTransaccionDTO.size())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testObtenerTransaccionPorId() throws Exception {
        System.out.println("testObtenerTransaccionPorId");

        when(transaccionService.obtenerTransaccionPorId(any())).thenReturn(transaccionDTO);

        ResultActions response = this.mockMvc.perform(get("/api/transacciones/".concat(transaccionDTO.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(transaccionDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testActualizarTransaccion() throws Exception {
        System.out.println("testActualizarTransaccion");

        when(transaccionService.actualizarTransaccion(any(), any())).thenReturn(transaccionDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/transacciones/".concat(transaccionDTOUpdate.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transaccionDTOUpdate)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.monto", CoreMatchers.is(transaccionDTOUpdate.getMonto().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarTransaccion() throws Exception {
        System.out.println("testEliminarTransaccion");

        Long id = 1L;
        doNothing().when(transaccionService).eliminarTransaccion(ArgumentMatchers.any());

        ResultActions response = this.mockMvc.perform(delete("/api/transacciones/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
