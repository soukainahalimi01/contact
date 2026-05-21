package com.contact.gestion.contact.contacts.service;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    // CREATE
    public Contact createContact(Contact contact) {
        if (contactRepository.findByCin(contact.getCin()).isPresent()) {
            throw new RuntimeException("Erreur : Un contact avec ce CIN existe déjà !");
        }

        // 2. Vérifier si l'Email existe déjà dans la base de données
        if (contactRepository.findByEmail(contact.getEmail()).isPresent()) {
            throw new RuntimeException("Erreur : Un contact avec cet Email existe déjà !");
        }
        return contactRepository.save(contact);
    }

    // READ ALL
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    // READ BY ID
    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
    }

    // UPDATE
    public Contact updateContact(Long id, Contact updatedContactData) {
        // 1. Vérifier si le contact qu'on veut modifier existe bien dans la base de données
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé avec l'id: " + id));

        // 2. Vérification du CIN : s'il existe déjà chez un AUTRE contact, on bloque !
        java.util.Optional<Contact> contactWithSameCin = contactRepository.findByCin(updatedContactData.getCin());
        if (contactWithSameCin.isPresent() && !contactWithSameCin.get().getId().equals(id)) {
            throw new RuntimeException("Erreur : Impossible, ce contact existe déjà !");
        }

        // 3. Vérification de l'Email : s'il existe déjà chez un AUTRE contact, on bloque !
        java.util.Optional<Contact> contactWithSameEmail = contactRepository.findByEmail(updatedContactData.getEmail());
        if (contactWithSameEmail.isPresent() && !contactWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Erreur : Impossible, ce contact existe déjà !");
        }
        existingContact.setNom(updatedContactData.getNom());
        existingContact.setPrenom(updatedContactData.getPrenom());
        existingContact.setTel(updatedContactData.getTel());
        existingContact.setCin(updatedContactData.getCin());
        existingContact.setVille(updatedContactData.getVille());
        existingContact.setPays(updatedContactData.getPays());
        existingContact.setAdresse(updatedContactData.getAdresse());
        existingContact.setEmail(updatedContactData.getEmail());

        return contactRepository.save(existingContact);
    }

    // DELETE
    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        contactRepository.delete(contact);
    }
}