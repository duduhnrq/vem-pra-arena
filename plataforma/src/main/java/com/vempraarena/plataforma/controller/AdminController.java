package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Admin;
import com.vempraarena.plataforma.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Admin admin) {
        try {
            Admin adminSalvo = adminService.cadastrarAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(adminSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        try {
            Admin adminAutenticado = adminService.autenticar(admin.getLoginAdmin(), admin.getSenha());
            return ResponseEntity.ok(adminAutenticado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable UUID id, @RequestBody Admin admin) {
        try {
            Admin adminAtualizado = adminService.atualizarAdmin(id, admin);
            return ResponseEntity.ok(adminAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}