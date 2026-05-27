package com.contact.gestion.contact.controller;

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

    @PostMapping("/{contactId}")
    public Coordonnee create(@PathVariable Long contactId, @RequestBody Coordonnee coordonnee) {
        return contactRepository.findById(contactId).map(contact -> {
            contact.addCoordonnee(coordonnee);
            contactRepository.save(contact); // On sauvegarde le parent (Contact)
            return coordonnee;
        }).orElseThrow(() -> new RuntimeException("Contact non trouvé"));
    }

    @GetMapping
    public List<Coordonnee> getAll() {
        return coordonneeRepository.findAll();
    }

    @PutMapping("/{id}")
    public Coordonnee update(@PathVariable Long id, @RequestBody Coordonnee details) {
        return coordonneeRepository.findById(id).map(c -> {
            c.setType(details.getType());
            c.setValeur(details.getValeur());
            c.setLabel(details.getLabel());
            return coordonneeRepository.save(c);
        }).orElseThrow(() -> new RuntimeException("Coordonnée non trouvée"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coordonneeRepository.deleteById(id);
    }
}