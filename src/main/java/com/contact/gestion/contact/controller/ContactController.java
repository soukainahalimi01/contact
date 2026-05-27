package com.contact.gestion.contact.controller;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // 1. GET ALL
    @GetMapping
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    // 2. GET BY ID (C'est ici que tu verras le Contact + ses Coordonnées)
    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé avec l'id : " + id));
    }

    // 3. POST (Créer un nouveau contact)
    @PostMapping
    public Contact create(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    // 4. PUT (Modifier un contact existant)
    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact details) {
        return contactRepository.findById(id).map(contact -> {
            contact.setNom(details.getNom());
            contact.setPrenom(details.getPrenom());
            contact.setTel(details.getTel());
            contact.setCin(details.getCin());
            contact.setVille(details.getVille());
            contact.setPays(details.getPays());
            contact.setAdresse(details.getAdresse());
            contact.setEmail(details.getEmail());
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("Contact non trouvé"));
    }

    // 5. DELETE (Supprimer un contact)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }
}