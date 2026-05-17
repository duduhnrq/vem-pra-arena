package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizarUsuarioSucesso() {
        UUID id = UUID.randomUUID();
        Usuario usuarioExistente = new Usuario("Antigo", "antigo@teste.com", "senha123");
        usuarioExistente.setId(id);

        Usuario novosDados = new Usuario("Novo", "novo@teste.com", "novaSenha");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.findByEmail("novo@teste.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("novaSenha")).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        Usuario resultado = usuarioService.atualizarUsuario(id, novosDados);

        assertEquals("Novo", resultado.getNome());
        assertEquals("novo@teste.com", resultado.getEmail());
        assertEquals("senhaCriptografada", resultado.getSenha());
        verify(usuarioRepository).save(usuarioExistente);
    }

    @Test
    void testAtualizarUsuarioEmailEmUso() {
        UUID id = UUID.randomUUID();
        UUID outroId = UUID.randomUUID();
        Usuario usuarioExistente = new Usuario("Antigo", "antigo@teste.com", "senha123");
        usuarioExistente.setId(id);

        Usuario outroUsuario = new Usuario("Outro", "novo@teste.com", "senha");
        outroUsuario.setId(outroId);

        Usuario novosDados = new Usuario("Novo", "novo@teste.com", null);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.findByEmail("novo@teste.com")).thenReturn(Optional.of(outroUsuario));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.atualizarUsuario(id, novosDados);
        });

        assertEquals("E-mail já está em uso.", exception.getMessage());
    }
}
