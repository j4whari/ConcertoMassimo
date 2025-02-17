package com.example.concertomassimo.controller;

import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/controller")
public class TicketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Metodo per la registrazione
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        // Verifica se l'utente esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/showRegister?error=Email già in uso!"; // Reindirizza con un messaggio di errore
        }

        // Crea un nuovo utente
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAttempts(0);

        // Salva l'utente nel database
        userRepository.save(user);

        return "redirect:/showRegister?success=Registrazione avvenuta con successo!"; // Reindirizza con un messaggio di successo
    }

}
