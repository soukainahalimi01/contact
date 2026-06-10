package com.contact.gestion.contact.coordonnee.controller;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.model.ContactDto;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
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

    // Récupère uniquement les contacts de l'utilisateur connecté
    @GetMapping
    public List<ContactDto> getAll(@AuthenticationPrincipal User user) {
        return contactRepository.findByUser(user)
                .stream()
                .map(c -> new ContactDto(
                        c.getId(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getTel(),
                        c.getCin(),
                        c.getVille(),
                        c.getPays(),
                        c.getAdresse(),
                        c.getEmail()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return contactRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé ou accès refusé"));
    }

    @PostMapping
    public Contact create(@RequestBody Contact contact, @AuthenticationPrincipal User user) {
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact details, @AuthenticationPrincipal User user) {
        return contactRepository.findByIdAndUser(id, user).map(contact -> {
            contact.setLastName(details.getLastName());
            contact.setFirstName(details.getFirstName());
            contact.setTel(details.getTel());
            contact.setCin(details.getCin());
            contact.setVille(details.getVille());
            contact.setPays(details.getPays());
            contact.setAdresse(details.getAdresse());
            contact.setEmail(details.getEmail());
            return contactRepository.save(contact);
        }).orElseThrow(() -> new RuntimeException("Contact non trouvé"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Contact contact = contactRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé"));
        contactRepository.delete(contact);
    }
}