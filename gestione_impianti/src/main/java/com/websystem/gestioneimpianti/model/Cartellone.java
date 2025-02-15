package com.websystem.gestioneimpianti.model;

import javax.persistence.*;

@Entity
@Table(name = "Cartellone")
public class Cartellone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "source", nullable = false)
    private String source;

    public Cartellone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Cartellone{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
