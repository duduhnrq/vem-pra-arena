package com.vempraarena.plataforma.repository;

import com.vempraarena.plataforma.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, UUID> {
    List<Ingresso> findByUsuarioId(UUID usuarioId);
    long countByEventoId(UUID eventoId);
}
