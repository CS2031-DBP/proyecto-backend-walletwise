/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Reporte.application;

import com.example.walletwise.Reporte.domain.ReporteService;
import com.example.walletwise.Reporte.domain.TipoReporte;
import com.example.walletwise.Reporte.dtos.ReporteDTO;
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
public class ReporteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReporteDTO reporteDTO;
    private ReporteDTO reporteDTOUpdate;

    private List<ReporteDTO> listReporteDTO;

    @BeforeEach
    public void setUp() {
        listReporteDTO = new ArrayList();
        reporteDTOUpdate = new ReporteDTO();
        reporteDTO = new ReporteDTO();

        reporteDTO.setId(1L);
        reporteDTO.setUsuarioId(1L);
        reporteDTO.setFechaGeneracion(LocalDate.now());
        reporteDTO.setTipoReporte(TipoReporte.INGRESOS);

        LocalDate inicio = LocalDate.parse("2024-05-12");
        LocalDate fin = LocalDate.parse("2024-10-12");

        reporteDTO.setFechaInicio(inicio);
        reporteDTO.setFechaFin(fin);
        reporteDTO.setFormato("PDF");

        reporteDTOUpdate.setId(1L);
        reporteDTOUpdate.setUsuarioId(1L);
        reporteDTOUpdate.setFechaGeneracion(LocalDate.now());
        reporteDTOUpdate.setTipoReporte(TipoReporte.INGRESOS);

        reporteDTOUpdate.setFechaInicio(inicio);
        reporteDTOUpdate.setFechaFin(fin);

        reporteDTOUpdate.setFormato("CSV");

    }

    @WithMockUser(username = "user01")
    @Test
    public void testCreaReporte() throws Exception {
        System.out.println("testCreaReporte");

        when(reporteService.crearReporte(any())).thenReturn(reporteDTO);

        ResultActions response = this.mockMvc.perform(post("/api/reportes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporteDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarReportes() throws Exception {
        System.out.println("testListarReportes");

        listReporteDTO.add(reporteDTO);

        when(reporteService.obtenerTodosLosReportes()).thenReturn(listReporteDTO);

        ResultActions response = this.mockMvc.perform(get("/api/reportes")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listReporteDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testObtenerReportePorId() throws Exception {
        System.out.println("testObtenerReportePorId");
        Long id = 1L;
        when(reporteService.obtenerReportePorId(any())).thenReturn(reporteDTO);

        ResultActions response = this.mockMvc.perform(get("/api/reportes/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(reporteDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testActualizarReporte() throws Exception {
        System.out.println("testActualizarReporte");

        Long id = 1L;
        reporteDTO.setFormato("CSV");
        when(reporteService.actualizarReporte(any(), any())).thenReturn(reporteDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/reportes/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporteDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.formato", CoreMatchers.is(reporteDTOUpdate.getFormato())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarReporte() throws Exception {

        System.out.println("testEliminarReporte");

        Long id = 1L;

        ResultActions response = this.mockMvc.perform(delete("/api/reportes/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
