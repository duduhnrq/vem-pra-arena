package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.repository.PromotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromotorService {

    @Autowired
    private PromotorRepository promotorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket.name}")
    private String supabaseBucketName;

    public Promotor cadastrarPromotor(Promotor promotor, MultipartFile cartaoCnpj, MultipartFile alteracaoContratual, MultipartFile documentoSocio) throws IOException {
        // Valida se o e-mail já existe
        Optional<Promotor> existente = promotorRepository.findByEmailCorporativo(promotor.getEmailCorporativo());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("O e-mail corporativo já está em uso.");
        }

        // Criptografa a senha antes de salvar
        promotor.setSenha(passwordEncoder.encode(promotor.getSenha()));

        // Upload dos arquivos para o Supabase
        String nomeCartaoCnpj = "cartaoCnpj_" + UUID.randomUUID().toString() + ".pdf";
        String nomeAlteracaoContratual = "alteracaoContratual_" + UUID.randomUUID().toString() + ".pdf";
        String nomeDocumentoSocio = "documentoSocio_" + UUID.randomUUID().toString() + ".pdf";

        String urlCartaoCnpj = uploadToSupabase(cartaoCnpj, nomeCartaoCnpj);
        String urlAlteracaoContratual = uploadToSupabase(alteracaoContratual, nomeAlteracaoContratual);
        String urlDocumentoSocio = uploadToSupabase(documentoSocio, nomeDocumentoSocio);

        promotor.setCaminhoCartaoCnpj(urlCartaoCnpj);
        promotor.setCaminhoAlteracaoContratual(urlAlteracaoContratual);
        promotor.setCaminhoDocumentoSocio(urlDocumentoSocio);

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

    private String uploadToSupabase(MultipartFile file, String fileName) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio não é permitido.");
        }

        String nomeOriginal = file.getOriginalFilename();
        if (nomeOriginal == null || !nomeOriginal.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Apenas arquivos PDF são permitidos para os documentos.");
        }

        String url = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, supabaseBucketName, fileName);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.set("apikey", supabaseKey);
        // Garante um Content-Type padrão se for nulo
        headers.set("Content-Type", file.getContentType() != null ? file.getContentType() : "application/pdf");

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return String.format("%s/storage/v1/object/public/%s/%s", supabaseUrl, supabaseBucketName, fileName);
        } else {
            throw new RuntimeException("Erro ao fazer upload para o Supabase: " + response.getBody());
        }
    }
}
