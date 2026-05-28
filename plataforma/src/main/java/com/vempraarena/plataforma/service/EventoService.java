package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.dto.EventoDTO;
import com.vempraarena.plataforma.model.Evento;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class EventoService {

    // Armazenamento simples em memória
    private static final Map<UUID, Evento> eventos = new ConcurrentHashMap<>();

    public List<Evento> listarTodos() {
        return new ArrayList<>(eventos.values());
    }

    public Optional<Evento> buscarPorId(UUID id) {
        return Optional.ofNullable(eventos.get(id));
    }
    
    /**
     * Lista todos os eventos de um promotor em ordem decrescente (mais recentes primeiro)
     */
    public List<Evento> listarEventosPorPromotor(Long promotorId) {
        return eventos.values().stream()
                .filter(e -> e.getPromotorId() != null && e.getPromotorId().equals(promotorId))
                .sorted(Comparator.comparing(Evento::getDataHora).reversed())
                .collect(Collectors.toList());
    }
    
    /**
     * Cria um novo evento a partir do DTO
     */
    public Evento criarEvento(Long promotorId, EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setId(UUID.randomUUID());
        evento.setPromotorId(promotorId);
        evento.setNome(eventoDTO.getTitulo());
        evento.setCategoria(eventoDTO.getCategoria());
        evento.setDescricaoBreve(eventoDTO.getDescricaoBreve());
        evento.setDescricaoDetalhada(eventoDTO.getDescricaoDetalhada());
        evento.setCapacidadeMaxima(eventoDTO.getPublico());
        evento.setDiasMontagem(eventoDTO.getDiasMontagem());
        evento.setImagemUrl(eventoDTO.getImagemUrl());
        evento.setRider(eventoDTO.getRider());
        evento.setDataHora(eventoDTO.getDataHora());
        
        // Calcula o preço
        BigDecimal preco = calcularPrecoEvento(eventoDTO.getPublico());
        evento.setPreco(preco);
        
        evento.setStatus("em_analise");
        evento.setCriadoEm(LocalDateTime.now());
        evento.setAtualizadoEm(LocalDateTime.now());
        
        eventos.put(evento.getId(), evento);
        return evento;
    }
    
    /**
     * Atualiza o status de um evento (admin only)
     */
    public Evento atualizarStatus(UUID id, String novoStatus) {
        Evento evento = eventos.get(id);
        
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }
        
        if (!isStatusValido(novoStatus)) {
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        }
        
        evento.setStatus(novoStatus);
        evento.setAtualizadoEm(LocalDateTime.now());
        return evento;
    }
    
    /**
     * Calcula o preço do evento baseado no público
     */
    private BigDecimal calcularPrecoEvento(Integer publico) {
        BigDecimal pautaBase = new BigDecimal("45000");
        
        if (publico > 40000) {
            pautaBase = new BigDecimal("120000");
        } else if (publico > 25000) {
            pautaBase = new BigDecimal("80000");
        }
        
        // Custo de segurança: 1.5 por pessoa, mínimo 5000
        BigDecimal seguranca = BigDecimal.valueOf(Math.max(5000, publico * 1.5));
        
        // Custo de limpeza: 4000 + 0.5 por pessoa
        BigDecimal limpeza = new BigDecimal("4000").add(
                BigDecimal.valueOf(publico * 0.5)
        );
        
        return pautaBase.add(seguranca).add(limpeza);
    }
    
    private boolean isStatusValido(String status) {
        return status.equals("em_analise") || 
               status.equals("confirmado") || 
               status.equals("rejeitado") || 
               status.equals("finalizado");
    }
}
