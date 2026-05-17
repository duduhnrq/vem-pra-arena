package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
