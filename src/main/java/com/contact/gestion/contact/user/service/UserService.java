package com.contact.gestion.contact.user.service;

import com.contact.gestion.contact.user.model.User;
import com.contact.gestion.contact.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ===== UserDetailsService =====
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ===== Register =====
    public User ajouterUser(User u) {
        if (userRepository.existsByEmail(u.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return userRepository.save(u);
    }

    // ===== Get All =====
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ===== Update =====
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User introuvable avec l'id : " + id));

        if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé par un autre utilisateur !");
        }

        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        existingUser.setDepartement(userDetails.getDepartement());
        existingUser.setRole(userDetails.getRole());

        return userRepository.save(existingUser);
    }

    // ===== Delete =====
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User introuvable avec l'id : " + id));
        userRepository.delete(existingUser);
    }

    // ===== Changer Mot de Passe ===== ← NOUVEAU
    public void changerMotDePasse(String email, String ancienMdp, String nouveauMdp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(ancienMdp, user.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        // Sauvegarder le nouveau mot de passe hashé en base
        user.setPassword(passwordEncoder.encode(nouveauMdp));
        userRepository.save(user);
    }
}