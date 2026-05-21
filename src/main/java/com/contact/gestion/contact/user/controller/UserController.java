package com.contact.gestion.contact.user.controller;

import com.contact.gestion.contact.user.model.user;
import com.contact.gestion.contact.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Endpoint pour l'inscription
    @PostMapping("/inscrire")
    public ResponseEntity<?> inscription(@RequestBody user utilisateur) {
        try {
            user nouveauUser = userService.inscrire(utilisateur);
            return ResponseEntity.ok(nouveauUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Endpoint pour l'authentification
    @PostMapping("/authentifier")
    public ResponseEntity<?> authentification(@RequestParam String email, @RequestParam String password) {
        boolean isSuccess = userService.authentifier(email, password);

        if (isSuccess) {
            return ResponseEntity.ok("Authentification réussie !");
        } else {
            return ResponseEntity.status(401).body("Erreur : Email ou mot de passe incorrect !");
        }
    }
}