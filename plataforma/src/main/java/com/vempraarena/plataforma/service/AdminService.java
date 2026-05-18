package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Admin;
import com.vempraarena.plataforma.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Admin cadastrarAdmin(Admin admin) {
        if (adminRepository.findByLoginAdmin(admin.getLoginAdmin()).isPresent()) {
            throw new IllegalArgumentException("Login já está em uso.");
        }

        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        return adminRepository.save(admin);
    }

    public Admin autenticar(String loginAdmin, String senha) {
        Admin admin = adminRepository.findByLoginAdmin(loginAdmin)
                .orElseThrow(() -> new IllegalArgumentException("Login ou senha inválidos."));

        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        return admin;
    }

    public Admin atualizarAdmin(UUID id, Admin dadosAtualizados) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin não encontrado."));

        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isBlank()) {
            admin.setNome(dadosAtualizados.getNome());
        }

        if (dadosAtualizados.getLoginAdmin() != null && !dadosAtualizados.getLoginAdmin().isBlank()) {
            adminRepository.findByLoginAdmin(dadosAtualizados.getLoginAdmin()).ifPresent(a -> {
                if (!a.getId().equals(id)) {
                    throw new IllegalArgumentException("Login já está em uso.");
                }
            });
            admin.setLoginAdmin(dadosAtualizados.getLoginAdmin());
        }

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isBlank()) {
            admin.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
        }

        return adminRepository.save(admin);
    }
}