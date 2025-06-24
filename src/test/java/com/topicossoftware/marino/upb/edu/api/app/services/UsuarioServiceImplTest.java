package com.topicossoftware.marino.upb.edu.api.app.services;

import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Rol;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.RolRepository;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.UpdatePasswordRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.UsuarioResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUsuarioSuccess() {
        UsuarioRequest request = new UsuarioRequest();
        request.setEmail("test@example.com");
        request.setNombres("Juan");
        request.setApellidos("Perez");
        request.setRol("USER");
        request.setCarnetIdentidad("12345");
        request.setCreadorID(1L);
        request.setCelular(78912345);
        request.setGenero(true);
        request.setFechaNacimiento(LocalDate.of(2000, 1, 1));

        Rol rol = new Rol();
        when(rolRepository.findByNombre("USER")).thenReturn(rol);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        Usuario savedUsuario = Usuario.builder()
                .usuarioId(42L)
                .email(request.getEmail())
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(savedUsuario);

        long result = usuarioService.addUsuario(request);

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testGetUsuarioByIdSuccess() {
        Rol rol = new Rol();
        rol.setNombre("ADMIN");

        Usuario user = Usuario.builder()
                .usuarioId(1L)
                .nombres("Ana")
                .apellidos("Lopez")
                .rol(rol)
                .createdAt(LocalDateTime.now())
                .email("ana@example.com")
                .createdBy(0)
                .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));

        UsuarioCompleteResponse response = usuarioService.getUsuarioById(1L);

        assertEquals("Ana", response.getNombres());
        assertEquals("Lopez", response.getApellidos());
        assertEquals("ADMIN", response.getRol());
        assertEquals("Sistema", response.getCreador());
    }

    @Test
    void testGetUsuarioByIdNotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        UsuarioServiceException exception = assertThrows(UsuarioServiceException.class, () -> {
            usuarioService.getUsuarioById(1L);
        });

        assertEquals("400", exception.getErrorCode());
    }

    @Test
    void testGetAllUsers() {
        Rol rol = new Rol();
        rol.setNombre("USER");

        Usuario user = Usuario.builder()
                .usuarioId(1L)
                .nombres("Carlos")
                .apellidos("Quispe")
                .rol(rol)
                .email("carlos@example.com")
                .build();

        when(usuarioRepository.findAll()).thenReturn(List.of(user));

        List<UsuarioResponse> result = usuarioService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNombres());
        assertEquals("USER", result.get(0).getRol());
    }

    @Test
    void testUpdatePasswordUsuario() {
        Usuario user = Usuario.builder()
                .usuarioId(1L)
                .password("oldPassword")
                .build();

        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setPassword("newSecret");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newSecret")).thenReturn("hashedNewSecret");

        usuarioService.updatePasswordUsuario(1L, request);

        assertEquals("hashedNewSecret", user.getPassword());
        verify(usuarioRepository).save(user);
    }

    @Test
    void testDeleteUsuarioById() {
        usuarioService.deleteUsuarioById(1L);
        verify(usuarioRepository).deleteById(1L);
    }
}