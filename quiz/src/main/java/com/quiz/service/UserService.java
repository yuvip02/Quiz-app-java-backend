package com.quiz.service;

import com.quiz.model.Users;
import com.quiz.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Users register(Users user) {
        // ✅ Encrypt password before saving
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

 // ✅ Add this import

    public ResponseEntity<?> verify(Users user) {
        Users existingUser = userRepo.findByUsernameIgnoreCase(user.getUsername()).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.status(401).body("{\"error\": \"User not found\"}"); // ✅ Return JSON
        }

        if (!encoder.matches(user.getPassword(), existingUser.getPassword())) {
            return ResponseEntity.status(401).body("{\"error\": \"Incorrect password\"}");
        }

        String token = jwtService.generateToken(user.getUsername());
        return ResponseEntity.ok("{\"token\": \"" + token + "\"}"); // ✅ Return token in JSON
    }



}

