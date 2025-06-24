package com.topicossoftware.marino.upb.edu.api.app.services;

import com.topicossoftware.marino.upb.edu.api.app.exceptions.CompradorServiceException;
import com.topicossoftware.marino.upb.edu.api.app.exceptions.UsuarioServiceException;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Comprador;
import com.topicossoftware.marino.upb.edu.api.domain.entity.Usuario;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.CompradorRepository;
import com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure.UsuarioRepository;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorCompleteResponse;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorRequest;
import com.topicossoftware.marino.upb.edu.api.domain.model.CompradorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompradorServiceImplTest {

    @InjectMocks
    private CompradorServiceImpl compradorService;

    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCompradorSuccess() {
        CompradorRequest request = new CompradorRequest();
        request.setUsuarioId(1L);
        request.setNombres("Lucia");
        request.setApellidos("Mamani");
        request.setEmail("lucia@example.com");
        request.setCarnetIdentidad("12345678");
        request.setCelular(78900011);

        Usuario mockUsuario = Usuario.builder().usuarioId(1L).build();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(mockUsuario));

        Comprador saved = Comprador.builder()
                .compradorId(42L)
                .email("lucia@example.com")
                .celular(request.getCelular())
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .createdAt(LocalDateTime.now())
                .usuario(mockUsuario)
                .carnetIdentidad(request.getCarnetIdentidad())
                .build();
        when(compradorRepository.save(any(Comprador.class))).thenReturn(saved);

        long id = compradorService.addComprador(request);

        verify(compradorRepository).save(any(Comprador.class));
    }

    @Test
    void testAddCompradorUsuarioNotFound() {
        CompradorRequest request = new CompradorRequest();
        request.setUsuarioId(99L);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioServiceException.class, () -> compradorService.addComprador(request));
    }

    @Test
    void testGetCompradorByIdSuccess() {
        Comprador comprador = Comprador.builder()
                .compradorId(1L)
                .nombres("Luis")
                .apellidos("Torrez")
                .email("luis@example.com")
                .createdAt(LocalDateTime.now())
                .build();

        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));

        CompradorCompleteResponse response = compradorService.getCompradorById(1L);

        assertEquals("Luis", response.getNombres());
        assertEquals("Torrez", response.getApellidos());
        assertEquals("luis@example.com", response.getEmail());
    }

    @Test
    void testGetCompradorByIdNotFound() {
        when(compradorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CompradorServiceException.class, () -> compradorService.getCompradorById(1L));
    }

    @Test
    void testGetCompradores() {
        Comprador comprador = Comprador.builder()
                .compradorId(1L)
                .nombres("Ana")
                .apellidos("Flores")
                .email("ana@example.com")
                .carnetIdentidad("6733784")
                .celular(71530110)
                .build();

        when(compradorRepository.findAll()).thenReturn(List.of(comprador));

        List<CompradorResponse> result = compradorService.getCompradores();

        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getNombres());
        assertEquals("Flores", result.get(0).getApellidos());
        assertEquals("6733784", result.get(0).getCarnetIdentidad());
    }

    @Test
    void testDeleteCompradorById() {
        compradorService.deleteCompradorById(10L);
        verify(compradorRepository).deleteById(10L);
    }
}