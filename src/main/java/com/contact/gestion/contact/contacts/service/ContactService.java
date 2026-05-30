package com.contact.gestion.contact.contacts.service;

import com.contact.gestion.contact.contacts.model.Contact;
import com.contact.gestion.contact.contacts.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact createContact(Contact contact) {
        if (contactRepository.findByCin(contact.getCin()).isPresent()) {
            throw new RuntimeException("Erreur : Un contact avec ce CIN existe déjà !");
        }
        if (contactRepository.findByEmail(contact.getEmail()).isPresent()) {
            throw new RuntimeException("Erreur : Un contact avec cet Email existe déjà !");
        }
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
    }

    // UPDATE معدل بالأسماء الجديدة
    public Contact updateContact(Long id, Contact updatedContactData) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact non trouvé avec l'id: " + id));

        Optional<Contact> contactWithSameCin = contactRepository.findByCin(updatedContactData.getCin());
        if (contactWithSameCin.isPresent() && !contactWithSameCin.get().getId().equals(id)) {
            throw new RuntimeException("Erreur : Impossible, ce contact existe déjà !");
        }

        Optional<Contact> contactWithSameEmail = contactRepository.findByEmail(updatedContactData.getEmail());
        if (contactWithSameEmail.isPresent() && !contactWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Erreur : Impossible, ce contact existe déjà !");
        }

        // التحديث للأسماء الجديدة المعتمدة في المخطط
        existingContact.setLastName(updatedContactData.getLastName());
        existingContact.setFirstName(updatedContactData.getFirstName());
        existingContact.setTel(updatedContactData.getTel());
        existingContact.setCin(updatedContactData.getCin());
        existingContact.setVille(updatedContactData.getVille());
        existingContact.setPays(updatedContactData.getPays());
        existingContact.setAdresse(updatedContactData.getAdresse());
        existingContact.setEmail(updatedContactData.getEmail());

        return contactRepository.save(existingContact);
    }

    public void deleteContact(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        contactRepository.delete(contact);
    }
}