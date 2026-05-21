package com.vempraarena.plataforma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita o CSRF 
                .csrf(AbstractHttpConfigurer::disable)
                
                // Configuração do CORS para permitir o front (agora vai pegar edu kk)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // Configuração de autorização de rotas
                .authorizeHttpRequests(auth -> auth
                        
                        .requestMatchers("/*.html", "/css/**", "/images/**", "/js/**").permitAll()
                        
                        // Rotas públicas (Autenticação e Cadastro)
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/admin/cadastrar").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/api/promotores/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/promotores/cadastrar").permitAll()
                        
                        //basicamente para rotas publicas ou eventos
                        .requestMatchers(HttpMethod.GET, "/api/events/**").permitAll()
                        
                        // comprar ingresso ou alterar dados
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //  todas as origens, só pega no live server se for assim
        configuration.setAllowedOriginPatterns(List.of("*")); 
        configuration.setAllowCredentials(true);
        // http basico liberado para o frontend, se for diferente do live server tem que ser assim
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
