package com.example.concertomassimo.controller;

import com.example.concertomassimo.dto.TicketRequest;
import com.example.concertomassimo.dto.TicketResponse;
import com.example.concertomassimo.model.Artista;
import com.example.concertomassimo.model.Order;
import com.example.concertomassimo.model.User;
import com.example.concertomassimo.repository.ArtistaRepository;
import com.example.concertomassimo.repository.OrderRepository;
import com.example.concertomassimo.repository.UserRepository;
import com.example.concertomassimo.service.TicketService;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequestMapping("/controller")
public class TicketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TicketService ticketService;

    // Metodo di login personalizzato
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletRequest request) {
        // Normalizza l'email
        email = email.toLowerCase().trim();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            // Crea il token con un ruolo (o senza, se non vuoi controlli sui ruoli)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))
                    );
            // Imposta il token nel SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authToken);

            // Salva manualmente il SecurityContext nella sessione
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            System.out.println("Auth after login = " + SecurityContextHolder.getContext().getAuthentication());

            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                return "redirect:/homepage";
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

        // Ottieni l'email dall'authentication
        String currentEmail = authentication.getName().toLowerCase().trim();
        Optional<User> optionalUser = userRepository.findByEmail(currentEmail);
        if (!optionalUser.isPresent()) {
            return "redirect:/auth?error=Utente non trovato!";
        }

        // Aggiorna i dati dell'utente
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

        userRepository.save(utente);

        Order order = new Order();
        order.setUser(utente);
        order.setMetodoConsegna(ticketRequest.getMetodoConsegna());
        order.setFatturaDifferente(ticketRequest.isFatturaDifferente());
        if(ticketRequest.isFatturaDifferente()){
            order.setFatturaIndirizzo(ticketRequest.getFatturaIndirizzo());
            order.setFatturaCivico(ticketRequest.getFatturaCivico());
            order.setFatturaCap(ticketRequest.getFatturaCap());
            order.setFatturaCitta(ticketRequest.getFatturaCitta());
        }

// Imposta i costi e il tipo di biglietto (assicurati che questi valori non siano null)
        order.setBiglietto(ticketRequest.getBiglietto() != null ? ticketRequest.getBiglietto() : BigDecimal.ZERO);
        order.setCommissioni(ticketRequest.getCommissioni() != null ? ticketRequest.getCommissioni() : BigDecimal.ZERO);
        order.setIva(ticketRequest.getIva() != null ? ticketRequest.getIva() : BigDecimal.ZERO);
        order.setTotale(ticketRequest.getTotale() != null ? ticketRequest.getTotale() : BigDecimal.ZERO);

// Salva il tipo di biglietto
        order.setTicketType(ticketRequest.getTicketType());

        orderRepository.save(order);

        return "pagamento";
    }


    @PostMapping("/aggiungiArtista")
    public String inserisciArtista(
            @RequestParam("nome") String nome,
            @RequestParam("audio_url") String audioUrl,
            @RequestParam("immagine") MultipartFile immagine) {

        try {
            byte[] immagineBytes = immagine.getBytes(); // Converte il file in array di byte
            // Salva l'artista nel database
            Artista artista = new Artista();
            artista.setNome(nome);
            artista.setAudioUrl(audioUrl);
            artista.setImmagine(immagineBytes); // Assicurati che il campo in DB sia di tipo BLOB

            artistaRepository.save(artista); // Salva nel database

            return "redirect:/homepage";
        } catch (IOException e) {
            return "redirect:/errore";
        }
    }


    @GetMapping("/eventi")
    public String mostraEventi(Model model) {
        List<Artista> artisti = artistaRepository.findAll();
        model.addAttribute("artisti", artisti);
        return "eventiInCorso";
    }
    @GetMapping("/generate-ticket")
    public ResponseEntity<byte[]> generateTicket() throws IOException, WriterException {
        // Recupera l'utente autenticato dal SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String email = authentication.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(404).build();
        }
        User user = optionalUser.get();

        // Recupera l'ultimo ordine effettuato dall'utente
        Order order = orderRepository.findTopByUserOrderByCreatedAtDesc(user);
        if (order == null) {
            return ResponseEntity.status(404).build();
        }
        String ticketType = order.getTicketType();
        String infoSupplementari = ""; // Puoi impostare altri dati se necessario

        // Genera il PDF usando i dati dell'utente e il tipo di biglietto
        byte[] ticket = ticketService.generateTicket(user.getNome(), user.getCognome(), ticketType, infoSupplementari);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "biglietto.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .body(ticket);
    }
}
