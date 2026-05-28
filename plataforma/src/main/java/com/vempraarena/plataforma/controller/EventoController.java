package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.dto.EventoDTO;
import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.service.EventoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * GET /api/eventos - Lista todos os eventos públicos
     */
    @GetMapping
    public ResponseEntity<List<Evento>> listar() {
        List<Evento> eventos = eventoService.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    /**
     * GET /api/eventos/{id} - Busca um evento específico
     */
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscar(@PathVariable UUID id) {
        return eventoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/eventos/meus - Lista eventos do promotor logado
     */
    @GetMapping("/meus")
    public ResponseEntity<?> listarMeusEventos(
            @RequestHeader(value = "X-Promotor-Id", required = false) Long promotorId) {
        if (promotorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Promotor não autenticado");
        }

        List<Evento> eventos = eventoService.listarEventosPorPromotor(promotorId);
        return ResponseEntity.ok(eventos);
    }

    /**
     * POST /api/eventos - Cria um novo evento
     * Requer autenticação (promotor logado)
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody EventoDTO eventoDTO,
            @RequestHeader(value = "X-Promotor-Id", required = false) Long promotorId) {
        if (promotorId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Promotor não autenticado. Faça login primeiro.");
        }

        try {
            Evento evento = eventoService.criarEvento(promotorId, eventoDTO);
            System.out.println("Evento criado: " + evento.getId() + " - " + evento.getNome()); // Log para depuração
            return ResponseEntity.status(HttpStatus.CREATED).body(evento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * PATCH /api/eventos/{id}/status - Atualiza o status de um evento (admin)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable UUID id,
            @RequestParam String status,
            HttpSession session) {
        // Validação básica de admin
        String role = (String) session.getAttribute("role");

        if (!"admin".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Apenas administradores podem atualizar status de eventos");
        }

        try {
            Evento evento = eventoService.atualizarStatus(id, status);
            return ResponseEntity.ok(evento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
