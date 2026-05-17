package com.vempraarena.plataforma.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "promotores",
    indexes = {
        @Index(name = "idx_promotor_email", columnList = "emailCorporativo", unique = true)
    }
)
public class Promotor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String razaoSocial;

    @Column(nullable = false)
    private String nomeFantasia;

    private String telefoneFixo;

    @Column(nullable = false)
    private String whatsappNegocios;

    @Column(nullable = false)
    private String nomeRepresentante;

    @Column(nullable = false, unique = true)
    private String emailCorporativo;

    @Column(nullable = false)
    private String senha;

    private String caminhoCartaoCnpj;
    
    private String caminhoAlteracaoContratual;
    
    private String caminhoDocumentoSocio;
}
