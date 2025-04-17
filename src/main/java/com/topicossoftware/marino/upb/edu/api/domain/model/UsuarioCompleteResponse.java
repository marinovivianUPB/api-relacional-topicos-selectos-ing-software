package com.topicossoftware.marino.upb.edu.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCompleteResponse {
    private String email;
    private String nombres;
    private String apellidos;
    private String carnetIdentidad;
    private long celular;
    private Boolean genero;
    private String rol;
    private LocalDateTime createdAt;
    private LocalDate fechaNacimiento;
    private String creador;
}