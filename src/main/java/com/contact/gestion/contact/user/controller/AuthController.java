package com.contact.gestion.contact.user.controller;

import com.contact.gestion.contact.config.JwtUtil;
import com.contact.gestion.contact.config.RefreshToken;
import com.contact.gestion.contact.config.RefreshTokenRepository;
import com.contact.gestion.contact.config.RefreshTokenService;
import com.contact.gestion.contact.user.model.LoginRequest;
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

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = userService
                    .findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String accessToken = jwtUtil.generateToken(user);
            RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", accessToken);
            response.put("refreshToken", refreshToken.getToken());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }

    // ===== Refresh Token =====
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");

        Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(requestRefreshToken);

        if (refreshTokenOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Carte invalide ou inexistante.");
        }

        RefreshToken token = refreshTokenOpt.get();

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

    // ===== Changer Mot de Passe =====
    @PutMapping("/change-password")
    public ResponseEntity<?> changerMotDePasse(
            @RequestBody Map<String, String> body,
            @RequestHeader("Authorization") String authHeader
    ) {
        try {
            String token  = authHeader.replace("Bearer ", "");
            String email  = jwtUtil.extractEmail(token); // ✅ nom correct

            String ancienMdp  = body.get("ancienMotDePasse");
            String nouveauMdp = body.get("nouveauMotDePasse");

            userService.changerMotDePasse(email, ancienMdp, nouveauMdp);

            return ResponseEntity.ok("Mot de passe mis à jour !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}