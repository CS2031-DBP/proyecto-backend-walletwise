/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.walletwise.Usuario.application;

import com.example.walletwise.Usuario.domain.Role;
import com.example.walletwise.Usuario.domain.UsuarioService;
import com.example.walletwise.Usuario.dtos.UsuarioResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
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
public class UsuarioControllerTest {
    
   @Autowired
    MockMvc mockMvc;

    @MockBean
    private UsuarioService presupuestoService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioResponseDTO usuarioDTO;
    private UsuarioResponseDTO usuarioDTOUpdate;
    private UsuarioResponseDTO adminDTO;

    private List<UsuarioResponseDTO> listUsuarioDTO;
    private Long usuarioId;

    @BeforeEach
    public void setUp() {
        usuarioId = 1L;
        listUsuarioDTO = new ArrayList();
        usuarioDTOUpdate = new UsuarioResponseDTO();
        usuarioDTO = new UsuarioResponseDTO();
        adminDTO = new UsuarioResponseDTO();
        
        adminDTO.setId(2L);
        adminDTO.setNombre("admin01");
        adminDTO.setEmail("admin01@gmail.com");
        adminDTO.setFechaRegistro(LocalDate.now());
        adminDTO.setRole(Role.ADMIN);
        
        usuarioDTO.setId(1L);
        usuarioDTO.setNombre("usuario01");
        usuarioDTO.setEmail("usuario01@gmail.com");
        usuarioDTO.setFechaRegistro(LocalDate.now());
        usuarioDTO.setRole(Role.USER);
        

        usuarioDTOUpdate = new UsuarioResponseDTO();
        usuarioDTOUpdate.setId(1L);
        usuarioDTOUpdate.setNombre("usuario001");
        usuarioDTOUpdate.setEmail("usuario01@gmail.com");
        usuarioDTOUpdate.setFechaRegistro(LocalDate.now());
        usuarioDTOUpdate.setRole(Role.USER);

    }

    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    @Test
    public void testCrearUsuario() throws Exception {
        System.out.println("testCrearUsuario");

        when(presupuestoService.crearUsuario(any())).thenReturn(usuarioDTO);

        ResultActions response = this.mockMvc.perform(post("/api/usuarios/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testCrearAdmin() throws Exception {
        System.out.println("testCrearAdmin");

        when(presupuestoService.crearAdmin(any())).thenReturn(adminDTO);

        ResultActions response = this.mockMvc.perform(post("/api/usuarios/admin/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDTO)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    
    
    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testObtenerUsuarioPorId() throws Exception {
        System.out.println("testObtenerUsuarioPorId");

        when(presupuestoService.obtenerUsuarioPorId(any())).thenReturn(usuarioDTO);

        ResultActions response = this.mockMvc.perform(get("/api/usuarios/".concat(usuarioDTO.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(usuarioDTO.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }
    
    
    @Test
    @WithMockUser(username = "user01@example.com", roles = {"USER"})
    public void testObtenerUsuarioPorEmail() throws Exception {
        System.out.println("testObtenerUsuarioPorEmail");

        when(presupuestoService.obtenerUsuarioPorEmail(any())).thenReturn(usuarioDTO);

        ResultActions response = this.mockMvc.perform(get("/api/usuarios/email/".concat(usuarioDTO.getEmail()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(usuarioDTO.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }
    

    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    @Test
    public void testListarTodasLasUsuarios() throws Exception {
        System.out.println("testListarUsuarios");

        listUsuarioDTO.add(usuarioDTO);

        when(presupuestoService.listarUsuarios()).thenReturn(listUsuarioDTO);

        ResultActions response = this.mockMvc.perform(get("/api/usuarios/listar")
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listUsuarioDTO.size())))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testActualizarUsuario() throws Exception {
        System.out.println("testActualizarUsuario");

        when(presupuestoService.actualizarUsuario(any(), any())).thenReturn(usuarioDTOUpdate);

        ResultActions response = this.mockMvc.perform(put("/api/usuarios/actualizar/".concat(usuarioDTOUpdate.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTOUpdate)));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(usuarioDTOUpdate.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    public void testEliminarUsuario() throws Exception {
        System.out.println("testEliminarUsuario");

        Long id = 1L;
        doNothing().when(presupuestoService).eliminarUsuario(ArgumentMatchers.any());

        ResultActions response = this.mockMvc.perform(delete("/api/usuarios/eliminar/".concat(id.toString()))
                .contentType(MediaType.APPLICATION_JSON));

        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }
    
}
