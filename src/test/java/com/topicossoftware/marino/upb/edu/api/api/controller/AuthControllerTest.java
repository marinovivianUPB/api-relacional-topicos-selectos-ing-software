package com.topicossoftware.marino.upb.edu.api.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topicossoftware.marino.upb.edu.api.TestConfig;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.app.AuthService;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@Import(TestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ReturnsCreatedWithToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest(); // Add username/password if needed
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("mocked.jwt.token");

        Mockito.when(authService.login(any())).thenReturn(loginResponse);

        mockMvc.perform(post("/auth/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("mocked.jwt.token"));
    }
}