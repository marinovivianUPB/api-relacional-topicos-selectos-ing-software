package com.topicossoftware.marino.upb.edu.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
    private String email;
    private String nombres;
    private String apellidos;
    private String carnetIdentidad;
    private long celular;
    private Boolean genero;
    private String rol;
    private LocalDate fechaNacimiento;
    private long creadorID;
}