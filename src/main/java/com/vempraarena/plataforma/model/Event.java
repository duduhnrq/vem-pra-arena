package com.vempraarena.plataforma.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private String title;
    
    @Column(length = 1000) // Permite descrições mais longas
    private String description;
    
    private LocalDate date;
    private String location;
    private String imageUrl; // Aqui guardaremos o link que o Supabase vai gerar

    public Event() {}

    public Event(UUID id, String title, String description, LocalDate date, String location, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    // Getters e Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}