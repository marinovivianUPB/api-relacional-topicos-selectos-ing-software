package com.topicossoftware.marino.upb.edu.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CompradorRequest {
    private String email;
    private String nombres;
    private String apellidos;
    private String carnetIdentidad;
    private long celular;
    private long usuarioId;
}
