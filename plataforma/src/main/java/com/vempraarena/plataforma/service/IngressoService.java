package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.model.Ingresso;
import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.EventoRepository;
import com.vempraarena.plataforma.repository.IngressoRepository;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public IngressoService(IngressoRepository ingressoRepository, EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.ingressoRepository = ingressoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Ingresso comprarIngresso(UUID usuarioId, UUID eventoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        long ingressosVendidos = ingressoRepository.countByEventoId(eventoId);

        if (ingressosVendidos >= evento.getCapacidadeMaxima()) {
            throw new IllegalStateException("Evento esgotado");
        }

        Ingresso ingresso = new Ingresso(evento, usuario);
        return ingressoRepository.save(ingresso);
    }

    public List<Ingresso> listarIngressosDoUsuario(UUID usuarioId) {
        return ingressoRepository.findByUsuarioId(usuarioId);
    }
}
