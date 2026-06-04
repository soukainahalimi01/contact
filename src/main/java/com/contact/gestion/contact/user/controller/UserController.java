package com.contact.gestion.contact.user.controller;

import com.contact.gestion.contact.user.model.User;
import com.contact.gestion.contact.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> ajouter(@RequestBody User user) {
        try {
            User nouveauUser = userService.ajouterUser(user);
            return ResponseEntity.ok(nouveauUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifier(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User userModifie = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(userModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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