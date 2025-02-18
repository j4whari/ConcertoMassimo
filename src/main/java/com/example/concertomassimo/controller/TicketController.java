package com.example.concertomassimo.controller;

import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/controller")
public class TicketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Gestisce il login
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return "redirect:/index";  // Reindirizza alla home dopo il login
        } else {
            return "redirect:/auth?error=Credenziali non valide!";
        }
    }

    // Metodo per la registrazione
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        // Controllo se l'email ha un dominio valido
        if (!email.matches(".*@(gmail\\.com|yahoo\\.com|libero\\.it)$")) {
            return "redirect:/auth?error=Email non valida!"; // Reindirizza con un messaggio di errore
        }

        // Verifica se l'utente esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/auth?error=Email già in uso!"; // Reindirizza con un messaggio di errore
        }

        // Crea un nuovo utente
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAttempts(0);

        // Salva l'utente nel database
        userRepository.save(user);

        return "redirect:/auth?success=Registrazione avvenuta con successo!"; // Reindirizza con un messaggio di successo
    }

}
