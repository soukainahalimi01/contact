package com.contact.gestion.contact.user.service;

import com.contact.gestion.contact.user.model.user;
import com.contact.gestion.contact.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import nécessaire
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Injection de l'encodeur

    // 1. AJOUTER
    public user ajouterUser(user u) {
        if (userRepository.findByEmail(u.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        // Hachage du mot de passe avant enregistrement
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    // 2. AFFICHER TOUS
    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    // 3. MODIFIER
    public user updateUser(Long id, user userDetails) {
        user existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User introuvable avec l'id : " + id));

        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
                userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre utilisateur !");
        }

        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());

        // Hachage du mot de passe uniquement s'il est modifié
        existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        existingUser.setDepartement(userDetails.getDepartement());
        existingUser.setRole(userDetails.getRole());

        return userRepository.save(existingUser);
    }

    // 4. SUPPRIMER
    public void deleteUser(Long id) {
        user existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User introuvable avec l'id : " + id));
        userRepository.delete(existingUser);
    }
}