package com.topicossoftware.marino.upb.edu.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Compradores")
public class Comprador {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long compradorId;
    @Column(name="EMAIL")
    private String email;
    @Column(name="CARNETIDENTIDAD", unique = true)
    private String carnetIdentidad;
    @Column(name="CELULAR")
    private long celular;
    @Column(name="NOMBRES")
    private String nombres;
    @Column(name="APELLIDOS")
    private String apellidos;
    @ManyToOne
    @JoinColumn(name="usuarioId", nullable=false)
    private Usuario usuario;
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;
}