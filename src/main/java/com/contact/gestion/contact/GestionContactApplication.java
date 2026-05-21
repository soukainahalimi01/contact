package com.contact.gestion.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// Ajoute l'exclude ici pour désactiver la sécurité par défaut
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GestionContactApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionContactApplication.class, args);
    }

}