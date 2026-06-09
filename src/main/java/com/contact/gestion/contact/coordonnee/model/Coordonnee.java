package com.contact.gestion.contact.coordonnee.model;

import com.contact.gestion.contact.contacts.model.Contact;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Coordonnee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoordonnee;

    private String type;
    private String valeur;
    private String label;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @JsonBackReference
    private Contact contact;

    public Coordonnee() {
    }

    public Long getIdCoordonnee() {
        return idCoordonnee;
    }

    public void setIdCoordonnee(Long idCoordonnee) {
        this.idCoordonnee = idCoordonnee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}