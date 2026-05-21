package com.contact.gestion.contact.user.service;

import com.contact.gestion.contact.user.model.user;
import com.contact.gestion.contact.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. Inscription
    public user inscrire(user utilisateur) {
        if (userRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new RuntimeException("Erreur : Cet email est déjà utilisé !");
        }
        return userRepository.save(utilisateur);
    }

    // 2. Authentification
    public boolean authentifier(String email, String password) {
        Optional<user> utilisateurOpt = userRepository.findByEmail(email);

        if (utilisateurOpt.isPresent()) {
            return utilisateurOpt.get().getPassword().equals(password);
        }

        return false;
    }
}