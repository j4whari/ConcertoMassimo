package com.example.concertomassimo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "utente")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campi per l'autenticazione
    private String email;
    private String password;
    private int attempts;

    // Campi aggiuntivi per il ticket (dati utente)
    private String tipologia;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    private String nazione;
    private String provincia;
    private String citta;
    private String indirizzo;
    private String civico;
    private String cap;
    private String privacyMarketing;

    // Costruttore vuoto
    public User() {}

    // Costruttore completo (opzionale)
    public User(Long id, String email, String password, int attempts,
                String tipologia, String nome, String cognome, LocalDate dataNascita,
                String nazione, String provincia, String citta, String indirizzo,
                String civico, String cap, String privacyMarketing) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.attempts = attempts;
        this.tipologia = tipologia;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.nazione = nazione;
        this.provincia = provincia;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.civico = civico;
        this.cap = cap;
        this.privacyMarketing = privacyMarketing;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getAttempts() {
        return attempts;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getTipologia() {
        return tipologia;
    }
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }
    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getNazione() {
        return nazione;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }
    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }
    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getPrivacyMarketing() {
        return privacyMarketing;
    }
    public void setPrivacyMarketing(String privacyMarketing) {
        this.privacyMarketing = privacyMarketing;
    }
}

