package com.vempraarena.plataforma;

import com.vempraarena.plataforma.model.Admin;
import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.model.Usuario;
import com.vempraarena.plataforma.repository.AdminRepository;
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

}