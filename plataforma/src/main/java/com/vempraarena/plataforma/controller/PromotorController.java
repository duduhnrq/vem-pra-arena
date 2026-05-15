package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.service.PromotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/promotores")
public class PromotorController {

    @Autowired
    private PromotorService promotorService;

    // Pasta local segura para armazenar os uploads
    private static final String UPLOAD_DIR = "uploads/promotores/";

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(
            @RequestParam("cnpj") String cnpj,
            @RequestParam("razaoSocial") String razaoSocial,
            @RequestParam("nomeFantasia") String nomeFantasia,
            @RequestParam(value = "telefoneFixo", required = false) String telefoneFixo,
            @RequestParam("whatsappNegocios") String whatsappNegocios,
            @RequestParam("nomeRepresentante") String nomeRepresentante,
            @RequestParam("emailCorporativo") String emailCorporativo,
            @RequestParam("senha") String senha,
            @RequestParam("cartaoCnpj") MultipartFile cartaoCnpj,
            @RequestParam("alteracaoContratual") MultipartFile alteracaoContratual,
            @RequestParam("documentoSocio") MultipartFile documentoSocio
    ) {
        try {
            // Cria o diretório se não existir
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Salva os arquivos fisicamente e guarda apenas o caminho
            String caminhoCartaoCnpj = salvarArquivo(cartaoCnpj);
            String caminhoAlteracaoContratual = salvarArquivo(alteracaoContratual);
            String caminhoDocumentoSocio = salvarArquivo(documentoSocio);

            // Preenche o objeto
            Promotor promotor = new Promotor();
            promotor.setCnpj(cnpj);
            promotor.setRazaoSocial(razaoSocial);
            promotor.setNomeFantasia(nomeFantasia);
            promotor.setTelefoneFixo(telefoneFixo);
            promotor.setWhatsappNegocios(whatsappNegocios);
            promotor.setNomeRepresentante(nomeRepresentante);
            promotor.setEmailCorporativo(emailCorporativo);
            promotor.setSenha(senha);
            promotor.setCaminhoCartaoCnpj(caminhoCartaoCnpj);
            promotor.setCaminhoAlteracaoContratual(caminhoAlteracaoContratual);
            promotor.setCaminhoDocumentoSocio(caminhoDocumentoSocio);

            // Salva no banco de dados
            Promotor salvo = promotorService.cadastrarPromotor(promotor);

            // Boa prática: não retornar a senha criptografada na resposta
            salvo.setSenha(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar o cadastro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean sucesso = promotorService.loginPromotor(loginRequest.getEmailCorporativo(), loginRequest.getSenha());
        if (sucesso) {
            return ResponseEntity.ok().body("Login efetuado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha incorretos.");
        }
    }

    private String salvarArquivo(MultipartFile arquivo) throws IOException {
        if (arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio não é permitido.");
        }

        String nomeOriginal = arquivo.getOriginalFilename();
        if (nomeOriginal == null || !nomeOriginal.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Apenas arquivos PDF são permitidos para os documentos.");
        }

        // Renomeia com UUID para evitar Path Traversal, colisões e nomes maliciosos
        String nomeArquivoSeguro = UUID.randomUUID().toString() + ".pdf";
        Path destino = Paths.get(UPLOAD_DIR + nomeArquivoSeguro);

        Files.copy(arquivo.getInputStream(), destino);

        return destino.toString();
    }

    // DTO para receber os dados do login
    public static class LoginRequest {
        private String emailCorporativo;
        private String senha;

        public String getEmailCorporativo() {
            return emailCorporativo;
        }

        public void setEmailCorporativo(String emailCorporativo) {
            this.emailCorporativo = emailCorporativo;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}
