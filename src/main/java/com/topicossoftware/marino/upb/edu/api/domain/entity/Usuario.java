package com.topicossoftware.marino.upb.edu.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long usuarioId;
    @Column(name="EMAIL", unique = true)
    private String email;
    @Column(name="CARNETIDENTIDAD", unique = true)
    private String carnetIdentidad;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="NOMBRES")
    private String nombres;
    @Column(name="APELLIDOS")
    private String apellidos;
    @Column(name="CELULAR")
    private long celular;
    @Column(name="GENERO")
    private Boolean genero;
    @ManyToOne
    @JoinColumn(name="rolId", nullable=false)
    private Rol rol;
    @Column(name="CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name="FECHANACIMIENTO")
    private LocalDate fechaNacimiento;
    @Column(name="LAST_LOGIN")
    private LocalDateTime lastLogin;
    /*ID DE ADMINSTRADOR QUE CREÓ EL USUARIO
    * DE SER NULO, FUE CREADO POR EL SISTEMA*/
    @Column(name="CREATED_BY")
    private long createdBy;
}