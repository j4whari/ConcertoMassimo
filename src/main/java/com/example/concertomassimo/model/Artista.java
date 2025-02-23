package com.example.concertomassimo.model;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String audioUrl;
    @Lob
    private byte[] immagine;


    public Artista(Long id, String nome, String audioUrl, byte[] immagine) {
        this.id = id;
        this.nome = nome;
        this.audioUrl = audioUrl;
        this.immagine = immagine;
    }

    public Artista() {}



    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getImmagineBase64() {
        if (this.immagine != null) {
            return Base64.getEncoder().encodeToString(this.immagine);
        }
        return null;
    }
    public void setImmagine(byte[] immagine) { this.immagine = immagine; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
}
