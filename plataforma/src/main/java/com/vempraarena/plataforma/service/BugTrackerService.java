package com.vempraarena.plataforma.service;

import com.vempraarena.plataforma.model.BugReport;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BugTrackerService {
    private final List<BugReport> logDeErros = new ArrayList<>();

    public void capturar(Exception ex) {
        BugReport report = new BugReport(ex.getMessage(), ex.getClass().getSimpleName());
        logDeErros.add(report);
        
        System.err.printf("[%s] [BUG-TRACK] Erro crítico detectado: %s | Tipo: %s%n", 
            report.getDataHora(), report.getMensagem(), report.getTipoDeErro());
    }

    public List<BugReport> getHistorico() {
        return new ArrayList<>(logDeErros);
    }
}
