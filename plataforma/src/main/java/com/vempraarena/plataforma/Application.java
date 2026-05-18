package com.vempraarena.plataforma;

import com.vempraarena.plataforma.model.Admin;
import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.AdminRepository;
import com.vempraarena.plataforma.repository.EventoRepository;
import com.vempraarena.plataforma.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, EventoRepository eventoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Usuário de Teste
            if (usuarioRepository.count() == 0) {
                Usuario user = new Usuario();
                user.setNome("Torcedor Teste");
                user.setEmail("teste@arena.com");
                user.setSenha(passwordEncoder.encode("123456"));
                usuarioRepository.save(user);
            }

            // Eventos de Teste
            if (eventoRepository.count() == 0) {
                eventoRepository.save(new Evento(
                    "Clássico das Multidões", 
                    "Náutico vs Sport na Arena Pernambuco. O maior jogo do estado!", 
                    LocalDateTime.now().plusDays(7), 
                    "Arena Pernambuco - Setor Leste", 
                    45000, 
                    new BigDecimal("60.00")
                ));

                eventoRepository.save(new Evento(
                    "Rec Summer Fest", 
                    "O maior festival de verão de Pernambuco com grandes atrações nacionais.", 
                    LocalDateTime.now().plusDays(15), 
                    "Gramado da Arena", 
                    30000, 
                    new BigDecimal("120.00")
                ));
            }
        };
    }
}