/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Presupuesto.application;

import com.example.walletwise.Presupuesto.domain.PeriodoPresupuesto;
import com.example.walletwise.Presupuesto.domain.PresupuestoService;
import com.example.walletwise.Presupuesto.dtos.PresupuestoDTO;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PresupuestoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PresupuestoService presupuestoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PresupuestoDTO presupuestoDTO;
    private PresupuestoDTO presupuestoDTOUpdate;

    private List<PresupuestoDTO> listPresupuestoDTO;
    private Long usuarioId;

    @BeforeEach
    public void setUp() {
        usuarioId = 1L;
        listPresupuestoDTO = new ArrayList();
        presupuestoDTOUpdate = new PresupuestoDTO();
        presupuestoDTO = new PresupuestoDTO();
        presupuestoDTO.setId(1L);
        presupuestoDTO.setMontoTotal(new BigDecimal(3000));
        presupuestoDTO.setGastoActual(new BigDecimal(600));
        presupuestoDTO.setCategoriaId(1L);
        presupuestoDTO.setAlerta("80% del monto");
        presupuestoDTO.setPeriodo(PeriodoPresupuesto.MENSUAL);
        presupuestoDTO.setUsuarioId(usuarioId);

        presupuestoDTOUpdate.setId(1L);
        presupuestoDTOUpdate.setMontoTotal(new BigDecimal(3000));
        presupuestoDTOUpdate.setGastoActual(new BigDecimal(1200));
        presupuestoDTOUpdate.setCategoriaId(1L);
        presupuestoDTOUpdate.setAlerta("80% del monto");
        presupuestoDTOUpdate.setPeriodo(PeriodoPresupuesto.MENSUAL);
        presupuestoDTOUpdate.setUsuarioId(usuarioId);

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testCrearPresupuesto() throws Exception {
        System.out.println("testCrearPresupuesto");

        when(presupuestoService.crearPresupuesto(any())).thenReturn(presupuestoDTO);

        ResultActions response = this.mockMvc.perform(post("/api/presupuestos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(presupuestoDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarTodasLasPresupuestos() throws Exception {
        System.out.println("testListarPresupuestos");

        listPresupuestoDTO.add(presupuestoDTO);

        when(presupuestoService.obtenerTodosLosPresupuestos()).thenReturn(listPresupuestoDTO);

        ResultActions response = this.mockMvc.perform(get("/api/presupuestos")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listPresupuestoDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testObtenerPresupuestosPorUsuario() throws Exception {
        System.out.println("testObtenerPresupuestosPorUsuario");

        listPresupuestoDTO.add(presupuestoDTO);
        when(presupuestoService.obtenerPresupuestosPorUsuarioId(any())).thenReturn(listPresupuestoDTO);

        ResultActions response = this.mockMvc.perform(get("/api/presupuestos/usuario/".concat(usuarioId.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listPresupuestoDTO.size())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testObtenerPresupuestoPorId() throws Exception {
        System.out.println("testObtenerPresupuestoPorId");

        when(presupuestoService.obtenerPresupuestoPorId(any())).thenReturn(presupuestoDTO);

        ResultActions response = this.mockMvc.perform(get("/api/presupuestos/".concat(presupuestoDTO.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(presupuestoDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testActualizarPresupuesto() throws Exception {
        System.out.println("testActualizarPresupuesto");

        when(presupuestoService.actualizarPresupuesto(any(), any())).thenReturn(presupuestoDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/presupuestos/".concat(presupuestoDTOUpdate.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(presupuestoDTOUpdate)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.gastoActual", CoreMatchers.is(presupuestoDTOUpdate.getGastoActual().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarPresupuesto() throws Exception {
        System.out.println("testEliminarPresupuesto");

        Long id = 1L;
        doNothing().when(presupuestoService).eliminarPresupuesto(ArgumentMatchers.any());

        ResultActions response = this.mockMvc.perform(delete("/api/presupuestos/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
