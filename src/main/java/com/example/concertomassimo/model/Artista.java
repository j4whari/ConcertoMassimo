package com.example.concertomassimo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String link;
    @Lob
    private byte[] immagine;


    public Artista(Long id, String nome, String link, byte[] immagine) {
        this.id = id;
        this.nome = nome;
        this.link = link;
        this.immagine = immagine;
    }

    public Artista() {}



    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public byte[] getImmagine() { return immagine; }
    public void setImmagine(byte[] immagine) { this.immagine = immagine; }

}
