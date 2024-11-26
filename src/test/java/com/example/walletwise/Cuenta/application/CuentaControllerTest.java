/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Cuenta.application;

import com.example.walletwise.Cuenta.domain.CuentaService;
import com.example.walletwise.Cuenta.domain.Moneda;
import com.example.walletwise.Cuenta.domain.TipoCuenta;
import com.example.walletwise.Cuenta.dtos.CuentaDTO;
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
public class CuentaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CuentaDTO cuentaDTO;
    private CuentaDTO cuentaDTOUpdate;

    private List<CuentaDTO> listCuentaDTO;

    @BeforeEach
    public void setUp() {
        listCuentaDTO = new ArrayList();
        cuentaDTOUpdate = new CuentaDTO();
        cuentaDTO = new CuentaDTO();
        Long userId = 1L;
        cuentaDTO.setId(1L);
        cuentaDTO.setNombre("Cta Ahorros");
        cuentaDTO.setMoneda(Moneda.PEN);
        cuentaDTO.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaDTO.setUsuarioId(userId);
        cuentaDTO.setSaldo(new BigDecimal(5000));

        cuentaDTOUpdate.setId(1L);
        cuentaDTOUpdate.setNombre("Cta Ahorros");
        cuentaDTOUpdate.setMoneda(Moneda.PEN);
        cuentaDTOUpdate.setSaldo(new BigDecimal(3500));
        cuentaDTOUpdate.setTipoCuenta(TipoCuenta.CORRIENTE);
        cuentaDTOUpdate.setUsuarioId(userId);

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testCrearCuenta() throws Exception {
        System.out.println("testCrearCuenta");

        when(cuentaService.crearCuenta(any())).thenReturn(cuentaDTO);

        ResultActions response = this.mockMvc.perform(post("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarTodasLasCuentas() throws Exception {
        System.out.println("testListarCuentas");

        listCuentaDTO.add(cuentaDTO);

        when(cuentaService.obtenerTodasLasCuentas()).thenReturn(listCuentaDTO);

        ResultActions response = this.mockMvc.perform(get("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listCuentaDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testObtenerCuentaPorId() throws Exception {
        System.out.println("testObtenerCuentaPorId");

        when(cuentaService.obtenerCuentaPorId(any())).thenReturn(cuentaDTO);

        ResultActions response = this.mockMvc.perform(get("/api/cuentas/".concat(cuentaDTO.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(cuentaDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testActualizarCuenta() throws Exception {
        System.out.println("testActualizarCuenta");

        when(cuentaService.actualizarCuenta(any(), any())).thenReturn(cuentaDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/cuentas/".concat(cuentaDTOUpdate.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuentaDTOUpdate)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.saldo", CoreMatchers.is(cuentaDTOUpdate.getSaldo().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarCuenta() throws Exception {
        System.out.println("testEliminarCuenta");

        Long id = 1L;
        doNothing().when(cuentaService).eliminarCuenta(ArgumentMatchers.any());

        ResultActions response = this.mockMvc.perform(delete("/api/cuentas/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
