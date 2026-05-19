package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.repository.EventoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> buscarPorId(UUID id) {
        return eventoRepository.findById(id);
    }
}
