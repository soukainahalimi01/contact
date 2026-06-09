package com.contact.gestion.contact.contacts.model;

public class ContactDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String tel;
    private String email;

    public ContactDto(Long id, String firstName, String lastName, String tel, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.email = email;
    }

    // getters obligatoires
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getTel() { return tel; }
    public String getEmail() { return email; }
}
