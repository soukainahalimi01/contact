package com.contact.gestion.contact.contacts.model;

import com.contact.gestion.contact.model.Coordonnee;
import com.contact.gestion.contact.user.model.User; // تأكدي أنك بدلتِها هنا
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String tel;
    private String cin;
    private String ville;
    private String pays;
    private String adresse;
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coordonnee> coordonnees = new ArrayList<>();

    public Contact() {}

    // الميثود اللي كتجمع الربط (Association Method)
    public void addCoordonnee(Coordonnee coordonnee) {
        this.coordonnees.add(coordonnee);
        coordonnee.setContact(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Coordonnee> getCoordonnees() { return coordonnees; }
    public void setCoordonnees(List<Coordonnee> coordonnees) { this.coordonnees = coordonnees; }
}