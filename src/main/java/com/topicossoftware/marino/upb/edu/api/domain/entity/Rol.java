package com.topicossoftware.marino.upb.edu.api.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="Roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long rolId;
    @Column(name="NOMBRE", unique = true)
    private String nombre;
}