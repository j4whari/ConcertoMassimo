package com.example.concertomassimo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordini")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    // Relazione ManyToOne con l'utente: un utente può avere più ordini
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Metodo di consegna: es. "corriere", "digitale", "negozio"
    @Column(nullable = false)
    private String metodoConsegna;

    // Indica se l'indirizzo di fatturazione è differente
    @Column(nullable = false)
    private boolean fatturaDifferente;

    // Campi per l'indirizzo di fatturazione alternativo (opzionali)
    private String fatturaIndirizzo;
    private String fatturaCivico;
    private String fatturaCap;
    private String fatturaCitta;

    // Costi relativi all'ordine
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal biglietto;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal commissioni;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal iva;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal totale;

    // Data e ora di creazione dell'ordine
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

