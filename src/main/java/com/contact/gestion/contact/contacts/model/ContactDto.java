package com.contact.gestion.contact.contacts.model;

public class ContactDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String tel;
    private String cin;
    private String ville;
    private String pays;
    private String adresse;
    private String email;
    private String addedByName;

    public ContactDto(Long id, String firstName, String lastName, String tel,
                      String cin, String ville, String pays, String adresse, String email,
                      String addedByName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.cin = cin;
        this.ville = ville;
        this.pays = pays;
        this.adresse = adresse;
        this.email = email;
        this.addedByName = addedByName;
    }

    // Getters
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTel() { return tel; }
    public String getCin() { return cin; }
    public String getVille() { return ville; }
    public String getPays() { return pays; }
    public String getAdresse() { return adresse; }
    public String getEmail() { return email; }
    public String getAddedByName() { return addedByName; }
}