package com.contact.gestion.contact.model;

import com.contact.gestion.contact.contacts.model.Contact;
import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore; // Import manquant ajouté

@Entity
@Data // Génère automatiquement les getters, setters, toString, etc.
public class Coordonnee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoordonnee;

    private String type;
    private String valeur;
    private String label;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @JsonIgnore // Utilisé pour éviter les boucles infinies lors de la sérialisation JSON
    private Contact contact;
}