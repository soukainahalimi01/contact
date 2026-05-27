package com.contact.gestion.contact.contacts.model;

import com.contact.gestion.contact.model.Coordonnee; // Import du modèle Coordonnee
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // Relation OneToMany : Un contact peut avoir plusieurs coordonnées
    // mappedBy = "contact" fait référence au champ 'contact' dans la classe Coordonnee
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coordonnee> coordonnees = new ArrayList<>();

    // 1. Constructeur vide (Obligatoire pour JPA)
    public Contact() {
    }
    public void addCoordonnee(Coordonnee coordonnee) {
        this.coordonnees.add(coordonnee);
        coordonnee.setContact(this);
    }

    // 2. Constructeur avec arguments
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getters et Setters pour la relation Coordonnees
    public List<Coordonnee> getCoordonnees() { return coordonnees; }
    public void setCoordonnees(List<Coordonnee> coordonnees) { this.coordonnees = coordonnees; }
}