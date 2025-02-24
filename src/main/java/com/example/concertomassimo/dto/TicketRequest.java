package com.example.concertomassimo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TicketRequest {

    // Dati utente
    private String tipologia;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String email;
    private String nazione;
    private String provincia;
    private String citta;
    private String indirizzo;
    private String civico;
    private String cap;
    private String privacyMarketing; // "acconsento" oppure "non_acconsento"

    // Dati ordine
    private String metodoConsegna; // "corriere", "digitale", "negozio"
    private boolean fatturaDifferente;
    private String fatturaIndirizzo;
    private String fatturaCivico;
    private String fatturaCap;
    private String fatturaCitta;

    // Nuovo campo per il tipo di biglietto (es. "Standard", "VIP", ecc.)
    private String ticketType;

    // Il prezzo del biglietto (BigDecimal)
    private BigDecimal biglietto;

    private BigDecimal commissioni;
    private BigDecimal iva;
    private BigDecimal totale;

    // Getter e Setter per tutti i campi

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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public String getMetodoConsegna() {
        return metodoConsegna;
    }
    public void setMetodoConsegna(String metodoConsegna) {
        this.metodoConsegna = metodoConsegna;
    }
    public boolean isFatturaDifferente() {
        return fatturaDifferente;
    }
    public void setFatturaDifferente(boolean fatturaDifferente) {
        this.fatturaDifferente = fatturaDifferente;
    }
    public String getFatturaIndirizzo() {
        return fatturaIndirizzo;
    }
    public void setFatturaIndirizzo(String fatturaIndirizzo) {
        this.fatturaIndirizzo = fatturaIndirizzo;
    }
    public String getFatturaCivico() {
        return fatturaCivico;
    }
    public void setFatturaCivico(String fatturaCivico) {
        this.fatturaCivico = fatturaCivico;
    }
    public String getFatturaCap() {
        return fatturaCap;
    }
    public void setFatturaCap(String fatturaCap) {
        this.fatturaCap = fatturaCap;
    }
    public String getFatturaCitta() {
        return fatturaCitta;
    }
    public void setFatturaCitta(String fatturaCitta) {
        this.fatturaCitta = fatturaCitta;
    }
    public BigDecimal getBiglietto() {
        return biglietto;
    }
    public void setBiglietto(BigDecimal biglietto) {
        this.biglietto = biglietto;
    }
    public BigDecimal getCommissioni() {
        return commissioni;
    }
    public void setCommissioni(BigDecimal commissioni) {
        this.commissioni = commissioni;
    }
    public BigDecimal getIva() {
        return iva;
    }
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }
    public BigDecimal getTotale() {
        return totale;
    }
    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public String getTicketType() {
        return ticketType;
    }
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
