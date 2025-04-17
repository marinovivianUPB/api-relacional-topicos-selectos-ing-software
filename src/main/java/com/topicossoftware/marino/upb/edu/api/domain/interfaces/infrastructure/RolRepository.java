package com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure;

import com.topicossoftware.marino.upb.edu.api.domain.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}