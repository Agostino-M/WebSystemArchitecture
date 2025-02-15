package com.websystem.gestioneimpianti.model;

import javax.persistence.*;

@Entity
@Table(name = "Impianti")
public class Impianto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_impianto")
    private Long idImpianto;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "latitudine")
    private Float latitudine;

    @Column(name = "longitudine")
    private Float longitudine;

    @Column(name = "id_palinsesto")
    private Long idPalinsesto;

    @Column(name = "is_attivo")
    private Boolean isAttivo;

    public Impianto() {
    }

    public Long getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(Long idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    public Long getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(Long idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public boolean isAttivo() {
        return isAttivo != null && isAttivo;
    }

    public void setIsAttivo(boolean isAttivo) {
        this.isAttivo = isAttivo;
    }

    @Override
    public String toString() {
        return "Impianto{" +
                "idImpianto=" + idImpianto +
                ", descrizione='" + descrizione + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", idPalinsesto=" + idPalinsesto +
                ", isAttivo=" + isAttivo +
                '}';
    }
}
