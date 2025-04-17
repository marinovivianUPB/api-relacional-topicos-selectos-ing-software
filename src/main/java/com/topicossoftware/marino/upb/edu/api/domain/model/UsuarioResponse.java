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
public class UsuarioResponse {
    private long usuarioId;
    private String nombres;
    private String apellidos;
    private LocalDateTime createdAt;
    private String rol;
    private long createdBy;
}