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
        email = email.toLowerCase().trim();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            // Assegna un ruolo di base (ROLE_USER)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );
            SecurityContextHolder.getContext().setAuthentication(authToken);

            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return "redirect:/form";
            } else {
                return "redirect:/auth?error=Autenticazione fallita!";
            }
        } else {
            return "redirect:/auth?error=Credenziali non valide!";
        }
    }



    // Metodo per la registrazione
    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        // Normalizza l'email
        email = email.toLowerCase().trim();

        // Controlla il dominio dell'email
        if (!email.matches(".*@(gmail\\.com|yahoo\\.com|libero\\.it)$")) {
            return "redirect:/auth?error=Email non valida!";
        }
        // Controlla se l'email esiste già
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/auth?error=Email già in uso!";
        }

        // Crea un nuovo utente
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setAttempts(0);

        // Salva l'utente nel database
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

        // Recupera l'utente autenticato dal SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth?error=Utente non autenticato!";
        }

        // Otteniamo l'email dal principal
        String currentEmail = authentication.getName().toLowerCase().trim();

        Optional<User> optionalUser = userRepository.findByEmail(currentEmail);
        if (!optionalUser.isPresent()) {
            return "redirect:/auth?error=Utente non trovato!";
        }

        // Aggiorna i campi dell'utente con i dati inviati dal form
        User utente = optionalUser.get();
        utente.setTipologia(ticketRequest.getTipologia());
        utente.setNome(ticketRequest.getNome());
        utente.setCognome(ticketRequest.getCognome());
        utente.setDataNascita(ticketRequest.getDataNascita());
        utente.setNazione(ticketRequest.getNazione());
        utente.setProvincia(ticketRequest.getProvincia());
        utente.setCitta(ticketRequest.getCitta());
        utente.setIndirizzo(ticketRequest.getIndirizzo());
        utente.setCivico(ticketRequest.getCivico());
        utente.setCap(ticketRequest.getCap());
        utente.setPrivacyMarketing(ticketRequest.getPrivacyMarketing());

        // Salva l'utente aggiornato
        userRepository.save(utente);

        // Esempio di generazione di un ticket o PDF (se serve)
        // TicketResponse response = ticketService.createTicket(ticketRequest);
        // model.addAttribute("orderId", response.getOrderId());
        // model.addAttribute("userId", utente.getId());

        return "ticketConfirmation";
    }
}
