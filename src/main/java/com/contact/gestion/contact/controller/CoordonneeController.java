package com.contact.gestion.contact.controller;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import com.contact.gestion.contact.model.Coordonnee;
import com.contact.gestion.contact.repository.CoordonneeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordonnees")
public class CoordonneeController {

    @Autowired
    private CoordonneeRepository coordonneeRepository;

    @Autowired
    private ContactRepository contactRepository;

    // Ajouter une coordonnée à un contact existant (via son ID)
    @PostMapping("/{contactId}")
    public Coordonnee create(@PathVariable Long contactId, @RequestBody Coordonnee coordonnee) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));

        coordonnee.setContact(contact);
        return coordonneeRepository.save(coordonnee);
    }

    // Récupérer toutes les coordonnées
    @GetMapping
    public List<Coordonnee> getAll() {
        return coordonneeRepository.findAll();
    }
}