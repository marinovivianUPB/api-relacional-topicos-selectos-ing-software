package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topicossoftware.marino.upb.edu.api.TestConfig;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.UsuarioService;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import(TestConfig.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String DUMMY_TOKEN = "dummy.jwt.token";

    @Test
    void addUsuario_ReturnsCreated() throws Exception {
        UsuarioRequest request = new UsuarioRequest();
        Mockito.when(usuarioService.addUsuario(any())).thenReturn(1L);

        mockMvc.perform(post("/usuarios/")
                        .header("token", DUMMY_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void getUsuarioById_ReturnsUsuario() throws Exception {
        UsuarioCompleteResponse response = new UsuarioCompleteResponse();
        Mockito.when(usuarioService.getUsuarioById(1L)).thenReturn(response);

        mockMvc.perform(get("/usuarios/1")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsuarios_ReturnsList() throws Exception {
        UsuarioResponse mockUsuario = new UsuarioResponse();
        Mockito.when(usuarioService.getAllUsers()).thenReturn(List.of(mockUsuario));

        mockMvc.perform(get("/usuarios/")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUsuarioById_Succeeds() throws Exception {
        doNothing().when(usuarioService).deleteUsuarioById(1L);

        mockMvc.perform(delete("/usuarios/1")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }
}