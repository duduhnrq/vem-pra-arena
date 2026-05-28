package com.vempraarena.plataforma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Evento {

    private UUID id;
    private Long promotorId;
    private String nome;
    private String categoria;
    private String descricao;
    private String descricaoBreve;
    private String descricaoDetalhada;
    private LocalDateTime dataHora;
    private String local;
    private Integer capacidadeMaxima;
    private BigDecimal preco;
    private Integer diasMontagem;
    private String imagemUrl;
    private String rider;
    private String status = "em_analise";
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}