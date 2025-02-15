package com.websystem.gestioneimpianti.model;

import javax.persistence.*;

@Entity
@Table(name = "Palinsesti")
public class Palinsesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_palinsesto")
    private Long idPalinsesto;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "path", nullable = false)
    private String path;

    public Long getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(Long idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Palinsesto{" +
                "idPalinsesto=" + idPalinsesto +
                ", nome='" + nome + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
