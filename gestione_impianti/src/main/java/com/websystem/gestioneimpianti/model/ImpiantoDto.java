package com.websystem.gestioneimpianti.model;

import java.time.LocalDateTime;

public class ImpiantoDto {

    private int idImpianto;
    private String descrizione;
    private float latitudine;
    private float longitudine;
    private LocalDateTime ultimaSegnalazione;
    private String stato;
    private int idPalinsesto;

    public ImpiantoDto() {
    }

    public int getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDateTime getUltimaSegnalazione() {
        return ultimaSegnalazione;
    }

    public void setUltimaSegnalazione(LocalDateTime ultimaSegnalazione) {
        this.ultimaSegnalazione = ultimaSegnalazione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
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

    public int getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(int idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }
}

