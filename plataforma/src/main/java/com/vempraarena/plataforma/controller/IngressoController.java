package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Ingresso;
import com.vempraarena.plataforma.service.IngressoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @PostMapping("/comprar")
    public ResponseEntity<?> comprar(@RequestBody Map<String, String> payload) {
        try {
            UUID usuarioId = UUID.fromString(payload.get("usuarioId"));
            UUID eventoId = UUID.fromString(payload.get("eventoId"));
            
            Ingresso ingresso = ingressoService.comprarIngresso(usuarioId, eventoId);
            return ResponseEntity.ok(ingresso);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao processar a compra: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ingresso>> listar(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(ingressoService.listarIngressosDoUsuario(usuarioId));
    }
}
