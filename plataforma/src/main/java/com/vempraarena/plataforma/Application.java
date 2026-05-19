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
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, EventoRepository eventoRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Criar Usuário de Teste no Banco Real se não existir
            if (usuarioRepository.findByEmail("teste@arena.com").isEmpty()) {
                Usuario user = new Usuario();
                user.setNome("Torcedor de Teste");
                user.setEmail("teste@arena.com");
                user.setSenha(passwordEncoder.encode("123456"));
                user.setCpf("000.000.000-00");
                user.setTelefone("(81) 99999-9999");
                user.setDataNascimento(LocalDate.of(1995, 1, 1));
                usuarioRepository.save(user);
                System.out.println(">>> Usuário de teste criado no Supabase!");
            }

            // Criar Eventos de Teste no Banco Real se estiver vazio
            if (eventoRepository.count() == 0) {
                eventoRepository.save(new Evento(
                    "Grande Final Arena", 
                    "O jogo decisivo da temporada na Arena Pernambuco.", 
                    LocalDateTime.now().plusDays(30), 
                    "Arena Pernambuco", 
                    45000, 
                    new BigDecimal("80.00")
                ));

                eventoRepository.save(new Evento(
                    "Festival de Música Retrô", 
                    "Os maiores sucessos dos anos 80 e 90 ao vivo.", 
                    LocalDateTime.now().plusDays(45), 
                    "Área Externa da Arena", 
                    25000, 
                    new BigDecimal("150.00")
                ));
                System.out.println(">>> Eventos iniciais criados no Supabase!");
            }
        };
    }
}