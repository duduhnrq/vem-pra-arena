package com.vempraarena.plataforma.exception;

import com.vempraarena.plataforma.service.BugTrackerService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Faz esta classe vigiar todo o projeto
public class GlobalExceptionHandler {

    private final BugTrackerService bugTracker;

    public GlobalExceptionHandler(BugTrackerService bugTracker) {
        this.bugTracker = bugTracker;
    }

    @ExceptionHandler(Exception.class) // Captura QUALQUER erro que aconteça
    public void gerenciarErro(Exception ex) {
        bugTracker.capturar(ex);
    }
}