package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.model.Ingresso;
import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.IngressoRepository;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoService eventoService; // ← busca evento em memória/JSON

    public IngressoService(IngressoRepository ingressoRepository, UsuarioRepository usuarioRepository, EventoService eventoService) {
        this.ingressoRepository = ingressoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoService = eventoService;
    }

    @Transactional
    public Ingresso comprarIngresso(UUID usuarioId, UUID eventoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Evento evento = eventoService.buscarPorId(eventoId) // ← sem BD
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        long ingressosVendidos = ingressoRepository.countByEventoId(eventoId);

        if (ingressosVendidos >= evento.getCapacidadeMaxima()) {
            throw new IllegalStateException("Evento esgotado");
        }

        Ingresso ingresso = new Ingresso(eventoId, usuario); // ← só o ID
        return ingressoRepository.save(ingresso);
    }

    public List<Ingresso> listarIngressosDoUsuario(UUID usuarioId) {
        return ingressoRepository.findByUsuarioId(usuarioId);
    }
}