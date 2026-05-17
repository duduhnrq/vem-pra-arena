package com.vempraarena.plataforma.controller;

import com.vempraarena.plataforma.model.Event;
import com.vempraarena.plataforma.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*") // Permite integração nativa com o front-end local
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("date") String date, // Recebe como String para converter para LocalDate
            @RequestParam("location") String location,
            @RequestParam("imagemBanner") MultipartFile imagemBanner
    ) {
        try {
            // Preenche o objeto do Evento baseado em POO
            Event event = new Event();
            event.setTitle(title);
            event.setDescription(description);
            event.setDate(LocalDate.parse(date)); // Converte "YYYY-MM-DD" para LocalDate
            event.setLocation(location);

            // Envia para o Service salvar no banco e processar a imagem
            Event salvo = eventService.cadastrarEvento(event, imagemBanner);

            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao salvar o evento: " + e.getMessage());
        }
    }

    // Aproveitando para deixar pronto o endpoint de listagem requisitado na US003
    @GetMapping
    public ResponseEntity<List<Event>> listar() {
        List<Event> lista = eventService.listarTodos();
        return ResponseEntity.ok(lista);
    }
}   