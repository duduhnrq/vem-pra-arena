package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Promotor;
import com.vempraarena.plataforma.service.PromotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/promotores")
public class PromotorController {

    @Autowired
    private PromotorService promotorService;

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

            // Salva no banco de dados e faz o upload dos arquivos via Supabase
            Promotor salvo = promotorService.cadastrarPromotor(promotor, cartaoCnpj, alteracaoContratual, documentoSocio);

            // Boa prática: não retornar a senha criptografada na resposta
            salvo.setSenha(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
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
