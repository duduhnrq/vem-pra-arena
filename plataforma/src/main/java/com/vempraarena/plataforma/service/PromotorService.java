package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.repository.PromotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotorService {

    @Autowired
    private PromotorRepository promotorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Promotor cadastrarPromotor(Promotor promotor) {
        // Valida se o e-mail já existe
        Optional<Promotor> existente = promotorRepository.findByEmailCorporativo(promotor.getEmailCorporativo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("O e-mail corporativo já está em uso.");
        }

        // Criptografa a senha antes de salvar
        promotor.setSenha(passwordEncoder.encode(promotor.getSenha()));

        // Os caminhos para os arquivos PDF (caminhoCartaoCnpj, caminhoAlteracaoContratual, caminhoDocumentoSocio)
        // já devem ter sido preenchidos na entidade recebida como parâmetro.

        return promotorRepository.save(promotor);
    }

    public boolean loginPromotor(String emailCorporativo, String senha) {
        Optional<Promotor> promotorOpt = promotorRepository.findByEmailCorporativo(emailCorporativo);
        if (promotorOpt.isPresent()) {
            Promotor promotor = promotorOpt.get();
            // Valida a segurança usando o matches do PasswordEncoder
            return passwordEncoder.matches(senha, promotor.getSenha());
        }
        return false;
    }
}
