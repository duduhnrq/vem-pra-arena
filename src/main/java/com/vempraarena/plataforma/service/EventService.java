package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Event;
import com.vempraarena.plataforma.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Se o grupo já tiver um serviço genérico de upload (ex: SupabaseService ou BucketService), 
    // você pode injetá-lo aqui. Por enquanto, simulamos o comportamento do promotorService.
    public Event cadastrarEvento(Event event, MultipartFile imagemBanner) throws Exception {
        
        // Validação simples
        if (event.getTitle() == null || event.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do evento é obrigatório.");
        }

        // TODO: Aqui a equipe pode ligar com o método de upload do Supabase que usaram no promotor
        // String urlImagem = supabaseService.upload(imagemBanner);
        // event.setImageUrl(urlImagem);
        
        // Provisório enquanto não liga o upload:
        event.setImageUrl("https://via.placeholder.com/600x400"); 

        return eventRepository.save(event);
    }

    public List<Event> listarTodos() {
        return eventRepository.findAll();
    }
}