package com.contact.gestion.contact.coordonnee.controller;

import com.contact.gestion.contact.coordonnee.model.Coordonnee;
import com.contact.gestion.contact.coordonnee.repository.CoordonneeRepository;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coordonnees")
@CrossOrigin(origins = "http://localhost:3000")
public class CoordonneeController {

    @Autowired
    private CoordonneeRepository coordonneeRepository;

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/{contactId}")
    public Coordonnee create(@PathVariable Long contactId,
                             @RequestBody Coordonnee coordonnee) {

        return contactRepository.findById(contactId)
                .map(contact -> {
                    coordonnee.setContact(contact);
                    return coordonneeRepository.save(coordonnee);
                })
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));
    }

    @GetMapping
    public List<Coordonnee> getAll() {
        return coordonneeRepository.findAll();
    }

    @GetMapping("/contact/{contactId}")
    public List<Coordonnee> getByContact(@PathVariable Long contactId) {
        return coordonneeRepository.findByContact_Id(contactId);
    }

    @PutMapping("/{id}")
    public Coordonnee update(@PathVariable Long id,
                             @RequestBody Coordonnee details) {

        Coordonnee coordonnee = coordonneeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordonnee non trouvée"));

        coordonnee.setType(details.getType());
        coordonnee.setValeur(details.getValeur());
        coordonnee.setLabel(details.getLabel());

        return coordonneeRepository.save(coordonnee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        coordonneeRepository.deleteById(id);
    }
}