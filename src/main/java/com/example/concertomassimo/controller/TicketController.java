package com.example.concertomassimo.controller;

import com.example.concertomassimo.dto.TicketRequest;
import com.example.concertomassimo.dto.TicketResponse;
import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.UserRepository;
import com.example.concertomassimo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/controller")
public class TicketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TicketService ticketService;

    // Metodo di login personalizzato
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            User user = userOptional.get();
            // Crea un token di autenticazione con il principal impostato come l'utente
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
            // Assicurati che il metodo getName() restituisca l'email: se il tuo modello User non implementa UserDetails,
            // puoi anche impostare il principal come l'email (ad es.: new UsernamePasswordAuthenticationToken(user.getEmail(), null, ...))
            SecurityContextHolder.getContext().setAuthentication(authToken);
            return "redirect:/index";  // Reindirizza alla home dopo il login
        } else {
            return "redirect:/auth?error=Credenziali non valide!";
        }
    }

    // Metodo per la registrazione (rimane invariato)
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        if (!email.matches(".*@(gmail\\.com|yahoo\\.com|libero\\.it)$")) {
            return "redirect:/auth?error=Email non valida!";
        }
        System.out.println("ciccio pasticcio");
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/auth?error=Email già in uso!";
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAttempts(0);
        userRepository.save(user);
        return "redirect:/auth?success=Registrazione avvenuta con successo!";
    }

    // Metodo per la creazione del ticket (aggiorna i dati anagrafici dell'utente autenticato)
    @PostMapping("/ticket")
    public String createTicket(@ModelAttribute TicketRequest ticketRequest,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth?error=Utente non autenticato!";
        }

        String currentEmail = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(currentEmail);
        if (!optionalUser.isPresent()) {
            return "redirect:/auth?error=Utente non trovato!";
        }

        User utente = optionalUser.get();
        // Aggiorna i campi dell'utente e salva
        // ...

        return "ticketConfirmation";
    }
}