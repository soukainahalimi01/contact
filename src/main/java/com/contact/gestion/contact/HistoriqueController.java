package com.contact.gestion.contact.historique.controller;

import com.contact.gestion.contact.historique.model.Historique;
import com.contact.gestion.contact.historique.repository.HistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueController {

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @GetMapping
    public List<Historique> getAll() {
        return historiqueRepository.findAll();
    }
}