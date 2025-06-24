package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topicossoftware.marino.upb.edu.api.TestConfig;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.CompradorService;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorCompleteResponse;

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

@WebMvcTest(CompradorController.class)
@Import(TestConfig.class)
class CompradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompradorService compradorService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String DUMMY_TOKEN = "dummy.jwt.token";

    @Test
    void addComprador_ReturnsCreated() throws Exception {
        CompradorRequest request = new CompradorRequest();
        Mockito.when(compradorService.addComprador(any())).thenReturn(1L);

        mockMvc.perform(post("/compradores/")
                        .header("token", DUMMY_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void getCompradorById_ReturnsComprador() throws Exception {
        CompradorCompleteResponse response = new CompradorCompleteResponse();
        Mockito.when(compradorService.getCompradorById(1L)).thenReturn(response);

        mockMvc.perform(get("/compradores/1")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void getAllCompradores_ReturnsList() throws Exception {
        CompradorResponse mockComprador = new CompradorResponse();
        Mockito.when(compradorService.getCompradores()).thenReturn(List.of(mockComprador));

        mockMvc.perform(get("/compradores/")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCompradorById_Succeeds() throws Exception {
        doNothing().when(compradorService).deleteCompradorById(1L);

        mockMvc.perform(delete("/compradores/1")
                        .header("token", DUMMY_TOKEN))
                .andExpect(status().isOk());
    }
}
