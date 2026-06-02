package com.contact.gestion.contact.contacts.repository;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.user.model.User; // Important !
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    // Ajout de ces méthodes pour permettre le filtrage par utilisateur
    List<Contact> findByUser(User user);
    Optional<Contact> findByIdAndUser(Long id, User user);

    // Méthodes pour éviter les doublons (que vous aviez besoin tout à l'heure)
    Optional<Contact> findByCin(String cin);
    Optional<Contact> findByEmail(String email);
}