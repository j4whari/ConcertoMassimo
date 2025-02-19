package com.example.concertomassimo.controller;

import com.example.concertomassimo.dto.TicketRequest;
import com.example.concertomassimo.dto.TicketResponse;
import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.UserRepository;
import com.example.concertomassimo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    // Metodo per la creazione del ticket (salvataggio dei dati del form nel database)
    @PostMapping("/ticket")
    public String createTicket(@ModelAttribute TicketRequest ticketRequest,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "ticketForm"; // Assicurati di avere una view per il form
        }

        // Recupera l'utente autenticato tramite il SecurityContext (ad es. con l'email)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(currentEmail);
        if (!optionalUser.isPresent()) {
            // Gestisci l'errore: l'utente non è stato trovato, ad esempio reindirizzando a una pagina di login
            return "redirect:/auth?error=Utente non trovato!";
        }
        User utente = optionalUser.get();

        // Aggiorna i campi dell'utente con i dati inviati dal form
        // NOTA: il campo password non viene toccato, rimane quello già registrato
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
        // Aggiorna gli altri campi se necessario (es. indirizzo per fattura, privacy, ecc.)

        // Salva l'utente aggiornato (la password rimane invariata)
        utente = userRepository.save(utente);

        // Se il TicketService genera un PDF o un ordine, puoi farlo qui
        TicketResponse response = ticketService.createTicket(ticketRequest);

        // Aggiungi i dati al model per la view di conferma
        model.addAttribute("orderId", response.getOrderId());
        model.addAttribute("userId", utente.getId());

        return "ticketConfirmation"; // Assicurati di avere il template corrispondente
    }
}