package com.vempraarena.plataforma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {
    
    private String titulo;
    private String categoria;
    private Integer publico;
    private LocalDateTime dataHora;
    private Integer diasMontagem;
    private String imagemUrl;
    private String descricaoBreve;
    private String descricaoDetalhada;
    private String rider;
    
    // Campos calculados (opcional, para resposta)
    private BigDecimal precoEstimado;
    private BigDecimal custoSeguranca;
    private BigDecimal custoLimpeza;
}
