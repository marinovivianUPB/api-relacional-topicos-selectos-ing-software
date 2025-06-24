package com.topicossoftware.marino.upb.edu.api.api.utils;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.topicossoftware.marino.upb.edu.api.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@Import(TestConfig.class)
@WebMvcTest
class JWTFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTVerifier verifier;

    @Test
    void shouldAllowAccessWhenTokenIsValid() throws Exception {
        DecodedJWT mockJwt = mock(DecodedJWT.class);
        when(verifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9")).thenReturn(mockJwt);

        mockMvc.perform(get("/usuarios/test")
                        .header("token", "valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authorized access"));
    }

    @Test
    void shouldRejectAccessWhenTokenIsInvalid() throws Exception {
        when(verifier.verify("123adc")).thenThrow(new JWTVerificationException("Invalid"));

        mockMvc.perform(get("/usuarios/test")
                        .header("token", "123adc"))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("Hubo un error al acceder el recurso"));
    }

    @Test
    void shouldRejectAccessWhenTokenMissing() throws Exception {
        mockMvc.perform(get("/usuarios/test"))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason("No se tienen las autorizaciones necesarias"));
    }
}