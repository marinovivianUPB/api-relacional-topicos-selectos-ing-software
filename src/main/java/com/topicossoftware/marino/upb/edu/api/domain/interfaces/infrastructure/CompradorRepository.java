package com.topicossoftware.marino.upb.edu.api.domain.interfaces.infrastructure;

import com.topicossoftware.marino.upb.edu.api.domain.entity.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Long> {
}