package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.Evento;
import com.vempraarena.plataforma.repository.EventoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private EventoService eventoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        Evento e1 = new Evento("Jogo de Futebol", "Descrição 1", LocalDateTime.now(), "Arena");
        Evento e2 = new Evento("Show Musical", "Descrição 2", LocalDateTime.now(), "Arena");
        
        when(eventoRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<Evento> resultado = eventoService.listarTodos();

        assertEquals(2, resultado.size());
        assertEquals("Jogo de Futebol", resultado.get(0).getNome());
        assertEquals("Show Musical", resultado.get(1).getNome());
    }
}
