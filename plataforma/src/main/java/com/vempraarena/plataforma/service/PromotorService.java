package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.repository.PromotorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PromotorService {

    private final PromotorRepository promotorRepository;
    private final PasswordEncoder passwordEncoder;

    public PromotorService(PromotorRepository promotorRepository, PasswordEncoder passwordEncoder) {
        this.promotorRepository = promotorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Promotor cadastrarPromotor(Promotor promotor) {
        if (promotor.getEmailCorporativo() == null || promotor.getEmailCorporativo().trim().isEmpty()) {
            throw new IllegalArgumentException("O e-mail corporativo é obrigatório.");
        }

        if (promotorRepository.findByEmailCorporativo(promotor.getEmailCorporativo()).isPresent()) {
            throw new IllegalArgumentException("E-mail corporativo já está em uso.");
        }

        if (promotor.getSenha() == null || promotor.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }

        promotor.setSenha(passwordEncoder.encode(promotor.getSenha()));

        // Ignore PDFs by mapping the fields to "limbo.pdf" as requested
        promotor.setCaminhoCartaoCnpj("limbo.pdf");
        promotor.setCaminhoAlteracaoContratual("limbo.pdf");
        promotor.setCaminhoDocumentoSocio("limbo.pdf");

        return promotorRepository.save(promotor);
    }

    public Promotor autenticar(String emailCorporativo, String senha) {
        Promotor promotor = promotorRepository.findByEmailCorporativo(emailCorporativo)
                .orElseThrow(() -> new IllegalArgumentException("E-mail corporativo ou senha inválidos."));

        if (!passwordEncoder.matches(senha, promotor.getSenha())) {
            throw new IllegalArgumentException("E-mail corporativo ou senha inválidos.");
        }

        return promotor;
    }

    public Promotor atualizarPromotor(Long id, Promotor dadosAtualizados) {
        Promotor promotor = promotorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotor não encontrado."));

        if (dadosAtualizados.getCnpj() != null && !dadosAtualizados.getCnpj().isBlank()) {
            promotor.setCnpj(dadosAtualizados.getCnpj());
        }
        if (dadosAtualizados.getRazaoSocial() != null && !dadosAtualizados.getRazaoSocial().isBlank()) {
            promotor.setRazaoSocial(dadosAtualizados.getRazaoSocial());
        }
        if (dadosAtualizados.getNomeFantasia() != null && !dadosAtualizados.getNomeFantasia().isBlank()) {
            promotor.setNomeFantasia(dadosAtualizados.getNomeFantasia());
        }
        if (dadosAtualizados.getTelefoneFixo() != null && !dadosAtualizados.getTelefoneFixo().isBlank()) {
            promotor.setTelefoneFixo(dadosAtualizados.getTelefoneFixo());
        }
        if (dadosAtualizados.getWhatsappNegocios() != null && !dadosAtualizados.getWhatsappNegocios().isBlank()) {
            promotor.setWhatsappNegocios(dadosAtualizados.getWhatsappNegocios());
        }
        if (dadosAtualizados.getNomeRepresentante() != null && !dadosAtualizados.getNomeRepresentante().isBlank()) {
            promotor.setNomeRepresentante(dadosAtualizados.getNomeRepresentante());
        }

        if (dadosAtualizados.getEmailCorporativo() != null && !dadosAtualizados.getEmailCorporativo().isBlank()) {
            promotorRepository.findByEmailCorporativo(dadosAtualizados.getEmailCorporativo()).ifPresent(p -> {
                if (!p.getId().equals(id)) {
                    throw new IllegalArgumentException("E-mail corporativo já está em uso.");
                }
            });
            promotor.setEmailCorporativo(dadosAtualizados.getEmailCorporativo());
        }

        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isBlank()) {
            promotor.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
        }

        return promotorRepository.save(promotor);
    }
}