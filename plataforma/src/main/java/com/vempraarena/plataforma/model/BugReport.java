package com.vempraarena.plataforma.model;

import java.time.LocalDateTime;

public class BugReport {

    private String mensagem;
    private String localDoErro;
    private LocalDateTime dataHora;

    // Construtor
    public BugReport(String mensagem, String localDoErro) {
        this.mensagem = mensagem;
        this.localDoErro = localDoErro;
        this.dataHora = LocalDateTime.now(); // Pega a hora exata do erro
    }

    // Getters
    public String getMensagem() { return mensagem; }
    public String getLocalDoErro() { return localDoErro; }
    public LocalDateTime getDataHora() { return dataHora; }
}
