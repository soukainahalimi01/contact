package com.contact.gestion.contact.model;

import com.contact.gestion.contact.contacts.model.Contact;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coordonnee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoordonnee;

    private String type;
    private String valeur;
    private String label;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @JsonIgnore
    private Contact contact;


}