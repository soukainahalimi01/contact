package com.contact.gestion.contact.controller;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import com.contact.gestion.contact.user.model.User;
import com.contact.gestion.contact.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé avec l'id : " + id));
    }

    @PostMapping("/user/{userId}")
    public Contact create(@PathVariable Long userId, @RequestBody Contact contact) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé avec l'id : " + userId));
        contact.setUser(user);
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public Contact update(@PathVariable Long id, @RequestBody Contact details) {
        return contactRepository.findById(id).map(contact -> {
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
    public void delete(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }
}