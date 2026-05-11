package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }
}
