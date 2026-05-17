package com.vempraarena.plataforma.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do evento é obrigatório")
    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    private String local;

    public Evento() {}

    public Evento(String nome, String descricao, LocalDateTime dataHora, String local) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.local = local;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
