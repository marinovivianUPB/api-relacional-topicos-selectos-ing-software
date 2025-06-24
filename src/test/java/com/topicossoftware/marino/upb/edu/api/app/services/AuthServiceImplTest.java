package com.topicossoftware.marino.upb.edu.api.app.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(); // si no tiene constructor
        ReflectionTestUtils.setField(authService, "usuarioRepository", usuarioRepository);
        ReflectionTestUtils.setField(authService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(authService, "algorithm", Algorithm.HMAC256("test-secret"));
    }
    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("secret");

        Usuario usuario = Usuario.builder()
                .usuarioId(123L)
                .email("user@example.com")
                .password("hashedPassword")
                .build();

        when(usuarioRepository.findByEmail("user@example.com")).thenReturn(usuario);
        when(passwordEncoder.matches("secret", "hashedPassword")).thenReturn(true);

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response.getToken());
        assertEquals(123L, response.getUsuarioId());
    }

    @Test
    void testLoginIncorrectPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("wrong");

        Usuario usuario = Usuario.builder()
                .email("user@example.com")
                .password("hashedPassword")
                .build();

        when(usuarioRepository.findByEmail("user@example.com")).thenReturn(usuario);
        when(passwordEncoder.matches("wrong", "hashedPassword")).thenReturn(false);

        UsuarioServiceException exception = assertThrows(UsuarioServiceException.class,
                () -> authService.login(loginRequest));

        assertEquals("404", exception.getErrorCode());
    }
}
