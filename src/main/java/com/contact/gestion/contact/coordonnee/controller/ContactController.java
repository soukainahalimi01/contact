package com.contact.gestion.contact.coordonnee.controller;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.model.ContactDto;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import com.contact.gestion.contact.user.model.Role;
import com.contact.gestion.contact.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    // Admin ET User voient TOUS les contacts (de tous les users).
    // La différence se fait uniquement sur les droits d'action (Edit/Del) côté frontend,
    // un USER ne pouvant agir que sur SES PROPRES contacts (addedByUserId == son id).
    @GetMapping
    public List<ContactDto> getAll(@AuthenticationPrincipal User user) {
        List<Contact> contacts = contactRepository.findAll();

        return contacts.stream()
                .map(c -> new ContactDto(
                        c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getTel(),
                        c.getCin(),
                        c.getVille(),
                        c.getPays(),
                        c.getAdresse(),
                        c.getEmail(),
                        c.getUser() != null
                                ? c.getUser().getFirstName() + " " + c.getUser().getLastName()
                                : null,
                        c.getUser() != null ? c.getUser().getId() : null
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));
    }

    // Seul un USER peut créer un contact ; ADMIN n'a pas le droit de créer
    @PostMapping
    public Contact create(@RequestBody Contact contact, @AuthenticationPrincipal User user) {
        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Un administrateur ne peut pas créer de contact.");
        }
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id,
                          @RequestBody Contact details,
                          @AuthenticationPrincipal User user) {

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("L'administrateur ne peut pas modifier un contact.");
        }

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));

        contact.setLastName(details.getLastName());
        contact.setFirstName(details.getFirstName());
        contact.setTel(details.getTel());
        contact.setCin(details.getCin());
        contact.setVille(details.getVille());
        contact.setPays(details.getPays());
        contact.setAdresse(details.getAdresse());
        contact.setEmail(details.getEmail());

        return contactRepository.save(contact);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal User user) {

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("L'administrateur ne peut pas supprimer un contact.");
        }

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));

        

        contactRepository.delete(contact);
    }
}