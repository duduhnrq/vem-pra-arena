package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.service.PromotorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotores")
public class PromotorController {

    private final PromotorService promotorService;

    public PromotorController(PromotorService promotorService) {
        this.promotorService = promotorService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Promotor promotor) {
        try {
            Promotor promotorSalvo = promotorService.cadastrarPromotor(promotor);
            return ResponseEntity.status(HttpStatus.CREATED).body(promotorSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Promotor promotor) {
        try {
            Promotor promotorAutenticado = promotorService.autenticar(promotor.getEmailCorporativo(), promotor.getSenha());
            return ResponseEntity.ok(promotorAutenticado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Promotor promotor) {
        try {
            Promotor promotorAtualizado = promotorService.atualizarPromotor(id, promotor);
            return ResponseEntity.ok(promotorAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}