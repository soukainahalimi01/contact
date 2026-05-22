package com.contact.gestion.contact.user.controller;

import com.contact.gestion.contact.user.model.user;
import com.contact.gestion.contact.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService; // Khdemna b UserService db direct!

    // 1. POST -> Ajouter : http://localhost:8080/api/users
    @PostMapping
    public ResponseEntity<?> ajouter(@Valid @RequestBody user user) {
        try {
            user nouveauUser = userService.ajouterUser(user);
            return ResponseEntity.ok(nouveauUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. GET -> Afficher tous : http://localhost:8080/api/users
    @GetMapping
    public ResponseEntity<List<user>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 3. PUT -> Modifier : http://localhost:8080/api/users/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @Valid @RequestBody user userDetails) {
        try {
            user userModifie = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(userModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. DELETE -> Supprimer : http://localhost:8080/api/users/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}