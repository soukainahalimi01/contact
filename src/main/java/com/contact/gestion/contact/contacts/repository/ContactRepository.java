package com.contact.gestion.contact.contacts.repository;

import com.contact.gestion.contact.contacts.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByCin(String cin);

    // Ajoute cette ligne pour l'Email
    Optional<Contact> findByEmail(String email);
}