package com.contact.gestion.contact.contacts.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String tel;
    private String cin;
    private String ville;
    private String pays;
    private String adresse;
    private String email;



    // 1. Empty Constructor (Required by JPA/Hibernate)
    public Contact() {
    }

    // 2. All Arguments Constructor
    public Contact(Long id, String nom, String prenom, String tel, String cin, String ville, String pays, String adresse, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.cin = cin;
        this.ville = ville;
        this.pays = pays;
        this.adresse = adresse;
        this.email = email;
    }

    // 3. Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}