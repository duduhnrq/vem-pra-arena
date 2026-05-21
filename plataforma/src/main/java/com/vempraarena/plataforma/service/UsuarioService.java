package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario.getAceitouTermos() == null || !usuario.getAceitouTermos()) {
            throw new IllegalArgumentException("É obrigatório aceitar os termos de uso e a política de privacidade.");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        usuario.setDataAceiteTermos(LocalDateTime.now());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("E-mail ou senha inválidos.");
        }

        return usuario;
    }

    public Usuario atualizarUsuario(java.util.UUID id, Usuario dadosAtualizados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()) {
            usuario.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getEmail() != null && !dadosAtualizados.getEmail().isBlank()) {
            usuarioRepository.findByEmail(dadosAtualizados.getEmail()).ifPresent(u -> {
                if (!u.getId().equals(id)) {
                    throw new IllegalArgumentException("E-mail já está em uso.");
                }
            });
            usuario.setEmail(dadosAtualizados.getEmail());
        }

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
        }

        if (dadosAtualizados.getCpf() != null && !dadosAtualizados.getCpf().isBlank()) {
            usuario.setCpf(dadosAtualizados.getCpf());
        }

        if (dadosAtualizados.getTelefone() != null && !dadosAtualizados.getTelefone().isBlank()) {
            usuario.setTelefone(dadosAtualizados.getTelefone());
        }

        if (dadosAtualizados.getDataNascimento() != null) {
            usuario.setDataNascimento(dadosAtualizados.getDataNascimento());
        }

        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(java.util.UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
