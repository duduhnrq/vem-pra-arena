package com.vempraarena.plataforma.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ingressos")
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataCompra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusIngresso status;

    public enum StatusIngresso {
        PAGO, CANCELADO, UTILIZADO
    }

    public Ingresso() {}

    public Ingresso(Evento evento, Usuario usuario) {
        this.evento = evento;
        this.usuario = usuario;
        this.dataCompra = LocalDateTime.now();
        this.status = StatusIngresso.PAGO; // Para simplificar o MVP
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public StatusIngresso getStatus() {
        return status;
    }

    public void setStatus(StatusIngresso status) {
        this.status = status;
    }
}
