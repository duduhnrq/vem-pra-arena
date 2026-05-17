package com.vempraarena.plataforma.repository;

import com.vempraarena.plataforma.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    // Prontinho! O JpaRepository já nos dá os métodos .save() e .findAll() de graça.
}