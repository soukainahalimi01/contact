package com.contact.gestion.contact.user.controller;

import com.contact.gestion.contact.config.JwtUtil;
import com.contact.gestion.contact.config.RefreshToken;
import com.contact.gestion.contact.config.RefreshTokenService;
import com.contact.gestion.contact.user.model.User;
import com.contact.gestion.contact.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    // ===== Register =====
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.ajouterUser(user);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ===== Login =====
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            User fullUser = (User) userService.loadUserByUsername(user.getEmail());
            String token = jwtUtil.generateToken(fullUser);

            // Création de la carte (Refresh Token) - Écrase l'ancienne s'il y en a une
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(fullUser);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("refreshToken", refreshToken.getToken());

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect !");
        }
    }

    // ===== Refresh Token Endpoint =====
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        // 1. Recherche du token en base
        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(requestRefreshToken);

        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Carte invalide ou inexistante.");
        }

        RefreshToken token = refreshTokenOpt.get();

        // 2. Vérification de l'expiration et génération
        try {
            refreshTokenService.verifyExpiration(token);

            User user = token.getUser();
            String accessToken = jwtUtil.generateToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", requestRefreshToken);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Carte expirée. Veuillez vous reconnecter.");
        }
    }
}