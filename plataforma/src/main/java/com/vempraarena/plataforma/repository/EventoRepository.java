package com.vempraarena.plataforma.repository;

import com.vempraarena.plataforma.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {
}
